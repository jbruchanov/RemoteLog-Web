package com.scurab.gwt.rlw.server.data;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.scurab.gwt.rlw.client.DataService;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.server.Queries;
import com.scurab.gwt.rlw.server.Queries.AppQuery;
import com.scurab.gwt.rlw.server.util.DataGenerator;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class DataServiceImpl extends RemoteServiceServlet implements DataService {

    /**
     * 
     */
    private static final long serialVersionUID = -4187117132460121962L;

    @Override
    public List<Device> getDevices(String app, int page) {

        List<Device> result = new ArrayList<Device>();

        Session s = Database.openSession();
        Query q = null;
        if (app == null) {
            q = s.createQuery("FROM Devices");
        }else{
            // create sql query
            AppQuery query = Queries.getQuery(Queries.QueryNames.SELECT_DEVS_BY_APP);
            q = s.createSQLQuery(query.Query).setResultTransformer(Transformers.aliasToBean(Device.class));
            String[] param = query.Parameters.toArray(new String[1]);
            q.setParameter(param[0], app);
        }

        q.setMaxResults(Application.PAGE_SIZE);
        if (page != 0)
            q.setFirstResult(page * Application.PAGE_SIZE);
        result.addAll(q.list());
        s.close();
        return result;
    }

    @Override
    public Device getDevice(int id) {
        return null;
    }

    @Override
    public List<String> getApplications() throws Exception {
        Session s = Database.openSession();
        AppQuery sql = Queries.getQuery(Queries.QueryNames.SELECT_APPS);
        List data = Database.getDataByQuery(s, sql.Query, sql.TYPE_SQL.equals(sql.Type));
        List<String> apps = new ArrayList<String>();
        for (int i = 0, n = data.size(); i < n; i++) {
            apps.add(data.get(i).toString());
        }
        s.close();
        return apps;
    }

    @Override
    public List<LogItem> getLogs(String app, int page) {
        return null;
    }
}
