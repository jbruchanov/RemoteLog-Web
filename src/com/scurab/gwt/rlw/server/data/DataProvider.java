package com.scurab.gwt.rlw.server.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.server.Database.TableInfo;
import com.scurab.gwt.rlw.server.Queries;
import com.scurab.gwt.rlw.server.Queries.AppQuery;
import com.scurab.gwt.rlw.shared.QueryNames;
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class DataProvider {

    /**
     * Get devices
     * 
     * @param app
     *            - optional application
     * @param page
     *            - page to download
     * @return
     */
    public List<Device> getDevices(HashMap<String, Object> params) {
        List<Device> result = new ArrayList<Device>();

        Session s = Database.openSession();
        Query q = null;
        // not much smart detection
        if (params.keySet().size() > 1) {
            AppQuery query = Queries.getQuery(QueryNames.SELECT_DEVS_BY_APP);
            q = s.createSQLQuery(query.Query).setResultTransformer(Transformers.aliasToBean(Device.class));
        } else {
            q = s.createQuery("FROM Devices");
        }
        initQuery(q, params);

        result.addAll(q.list());
        s.close();
        return result;
    }

    public List<String> getApplications() throws Exception {
        Session s = Database.openSession();
        AppQuery sql = Queries.getQuery(QueryNames.SELECT_APPS);
        List data = Database.getDataByQuery(s, sql.Query, AppQuery.TYPE_SQL.equals(sql.Type));
        List<String> apps = new ArrayList<String>();
        for (int i = 0, n = data.size(); i < n; i++) {
            apps.add(data.get(i).toString());
        }
        s.close();
        return apps;
    }

    public List<String> getDistinctValues(String query, String appName) {
        Session s = Database.openSession();
        AppQuery sql = Queries.getQuery(query);
        Query q = s.createSQLQuery(sql.Query);
        if (appName != null) {
            q.setParameter(SharedParams.APP_NAME, appName);
        }

        List data = q.list();
        List<String> apps = new ArrayList<String>();
        for (int i = 0, n = data.size(); i < n; i++) {
            apps.add(String.valueOf(data.get(i)));
        }
        s.close();
        return apps;
    }

    public List<LogItem> getLogs(HashMap<String, Object> params) {
        try {
            List<LogItem> result = new ArrayList<LogItem>();

            Session s = Database.openSession();
            Query q = getQuery(s, LogItem.class, params);

            result.addAll(q.list());
            s.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    private void initQuery(Query q) {
        initQuery(q, null);
    }

    /**
     * 
     * @param q
     * @param params
     */
    private void initQuery(Query q, HashMap<String, Object> params) {
        int page = 0;
        if (params.containsKey(SharedParams.PAGE)) {
            page = ((Number) params.get(SharedParams.PAGE)).intValue();
        }
        q.setMaxResults(SharedParams.PAGE_SIZE);
        if (page != 0) {
            q.setFirstResult(page * SharedParams.PAGE_SIZE);
        }
        // init params
        if (params != null) {
            for (String param : params.keySet()) {
                if (SharedParams.PAGE.equals(param)) {
                    continue;
                }
                q.setParameter(param, params.get(param));
            }
        }
    }

    protected Query getQuery(Session s, Class<?> clazz, HashMap<String, Object> srcParams)
            throws ClassNotFoundException {
        HashMap<String, Object> params = new HashMap<String, Object>(srcParams);
        int page = 0;
        if (params.containsKey(SharedParams.PAGE)) {
            page = ((Number) params.get(SharedParams.PAGE)).intValue();
            params.remove(SharedParams.PAGE);
        }

        boolean addedParams = false;

        TableInfo ti = Database.getTable(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("FROM %s", ti.TableName));
        if (params.size() > 0) {
            StringBuilder filter = new StringBuilder();
            filter.append(String.format(" WHERE ", ti.TableName));
            for (String key : params.keySet()) {
                String columnName = key;
                String v = params.get(columnName).toString();
                String op = (v.charAt(0) == '*' || v.charAt(v.length() - 1) == '*') ? "LIKE" : "=";
                filter.append(String.format("%1$s %2$s :%1$s AND ", key, op)); // http://www.stpe.se/2008/07/hibernate-hql-like-query-named-parameters/
                addedParams = true;
            }
            if (addedParams) {
                filter.setLength(filter.length() - "AND ".length());
                sb.append(filter.toString());
            }
        }

        if (ti.DefaultOrderString != null) {
            sb.append(String.format(" ORDER BY %s", ti.DefaultOrderString));
        }

        String qry = sb.toString();
        Query q = s.createQuery(qry);

        // set values for WHERE params
        if (addedParams) {
            for (String key : params.keySet()) {
                Object o = params.get(key);
                if (o instanceof Integer) {
                    q.setInteger(key, (Integer) o);
                } else if (o instanceof Double) {
                    q.setDouble(key, (Double) o);
                } else if (o instanceof String) {
                    String v = (String) o;
                    if (v.charAt(v.length() - 1) == '*') {
                        v = v.substring(0, v.length() - 1) + "%";
                    }
                    if (v.charAt(0) == '*') {
                        v = "%" + v.substring(1, v.length());
                    }
                    q.setString(key, v);
                } else if (o instanceof Date) {
                    Date d = (Date) o;
                    java.sql.Date sqld = new java.sql.Date(d.getTime());
                    q.setDate(key, sqld);
                } else {
                    throw new IllegalStateException("Not implemented!");
                }
            }
        }

        q.setMaxResults(SharedParams.PAGE_SIZE);
        if (page != 0) {
            q.setFirstResult(page * SharedParams.PAGE_SIZE);
        }
        return q;
    }
}
