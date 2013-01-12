package com.scurab.gwt.rlw.server.data.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.data.Database;
import com.scurab.gwt.rlw.server.data.Database.TableInfo;
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.DeviceRespond;
import com.scurab.gwt.rlw.shared.model.Respond;

/**
 * Servlet for handling Registration events<br/>
 * Supported methods:<br/>
 * <code>GET</code><br/>
 * <code>PUT</code> - Put only updates pushToken, input is request just simple value of pushToken
 * 
 * @author Joe Scurab
 *
 */
public class RegistrationConnector extends DataConnector<Device> {

    private static final String NICE = "/nice";
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session s = null;
        Respond<?> dr = null;

        try {
            boolean nice = false;
            String sid = null;
            String path = req.getPathInfo();
            
            //parse id and check if there is "nice" switch to format inner json string
            if (path == null) {
                throw new IllegalArgumentException("Missing id user /regs/{id}");
            } else {
                if (path.contains(NICE)) {
                    nice = true;
                    path = path.replace(NICE, "");
                }
                sid = path.replaceFirst("/", "");
            }

            //get data
            s = Database.openSession();
            int id = Integer.parseInt(sid);
            Device d = (Device) s.get(Device.class, id);
            if (d == null) {
                throw new IllegalArgumentException(String.format("Record with id:%s not found", id));
            }
            if (nice) {
                dr = new Respond<HashMap<?, ?>>(convert(d));
            } else {
                dr = new DeviceRespond(d);
            }

        } catch (Exception e) {
            dr = new DeviceRespond(e);
        } finally {
            String r = Application.GSON.toJson(dr);
            resp.getOutputStream().write(r.getBytes());
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
        resp.getOutputStream().close();
    }
    
    /**
     * Update push token
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session s = null;
        Respond<?> dr = null;
        try {
            //parse path
            String sid = null;
            String path = req.getPathInfo();
            String push = read(req.getInputStream());
            if (path == null) {
                throw new IllegalArgumentException("Missing id user /regs/{id}");
            } else {
                sid = path.replaceFirst("/", "");
            }
            
            s = Database.openSession();
            int id = Integer.parseInt(sid);
            Device d = (Device) s.get(Device.class, id);           
            d.setPushID(push);
            
            s.beginTransaction();
            s.saveOrUpdate(d);
            s.getTransaction().commit();
            
            dr = new Respond<String>(push);

        } catch (Exception e) {
            dr = new Respond<Exception>(e);
        } finally {
            String r = Application.GSON.toJson(dr);
            resp.getOutputStream().write(r.getBytes());
            if (s != null && s.isOpen()) {
                s.close();
            }
        }
        resp.getOutputStream().close();
    }

    private static HashMap convert(Device d) {
        String v = Application.GSON.toJson(d);
        HashMap obj = Application.GSON.fromJson(v, HashMap.class);
        String mDetail = d.getDetail();
        if (mDetail != null && mDetail.length() > 0 && mDetail.charAt(0) == '{') {
            obj.remove("Detail");
            obj.put("Detail", Application.GSON.fromJson(mDetail, TreeMap.class));
        }
        return obj;
    }

    @Override
    protected DeviceRespond onPostRequest(Session s, InputStream is) throws IOException {
        DeviceRespond response = null;
        String json = read(is);
        Device[] data = parse(json, json.charAt(0) == '[');
        Device[] saved = onWrite(s, data);
        if (saved.length == 0) {
            response = new DeviceRespond("Nothing saved!?", 0);
        } else if (saved.length == 1) {
            response = new DeviceRespond(saved[0]);
        } else {
            response = new DeviceRespond();
        }
        return response;
    }

    @Override
    protected Device[] onWrite(Session s, Device[] data) {
        s.beginTransaction();
        boolean uniqueUuid = Boolean.parseBoolean(Application.APP_PROPS
                .getProperty(SharedParams.CLIENT_UNIQUE_UUID_PER_DEVICE));
        TableInfo ti = Database.getTable(Device.class);
        String template = String.format("FROM %s WHERE DevUUID = :uuid", ti.TableName);
        for(int i = 0, n = data.length;i<n;i++){
            Device d = data[i];
            if (uniqueUuid) {
                String uuid = d.getDevUUID();
                Query q = s.createQuery(template);
                q.setString("uuid", uuid);
                java.util.List dbdata = q.list();
                if(dbdata.size() == 1){
                    Device dbDevice = (Device) dbdata.get(0);
                    dbDevice.updateValues(d);
                    d = dbDevice;
                    data[i] = d;
                }
            }
            s.saveOrUpdate(d);
        }
        s.getTransaction().commit();
        return data;
    }

    @Override
    public Class<?> getGenericClass() {
        return Device.class;
    }

    @Override
    public Class<?> getArrayGenericClass() {
        return Device[].class;
    }
}
