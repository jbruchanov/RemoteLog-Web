package com.scurab.gwt.rlw.server.data.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.data.DataProvider;
import com.scurab.gwt.rlw.shared.model.Respond;
import com.scurab.gwt.rlw.shared.model.Settings;
import com.scurab.gwt.rlw.shared.model.SettingsRespond;

/**
 * Servlet for handling settings<br/>
 * Supported methods:<br/>
 * <code>GET</code>
 * 
 * @author Joe Scurab
 *
 */
public class SettingsConnector extends DataConnector<Void> {

    /**
     * 
     */
    private static final long serialVersionUID = 3072901058334309712L;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        
        // url... /{deviceId}/appname/{appname} - example /1/appname/asbd
        Respond<Settings[]> respond = null;
        UrlValues val = null;
        try{
            val = new UrlValues(req.getPathInfo());
            int devId = Integer.parseInt(val.deviceId);
            Settings[] vals = new DataProvider().getSettingsForDevice(val.appName, devId);
            respond = new SettingsRespond(vals);
        }
        catch(Exception e){
            respond = new Respond<Settings[]>(e);
        }
        finally{
            String r = Application.toJson(respond);
            resp.getOutputStream().write(r.getBytes());
        }
        resp.getOutputStream().close();
    }

    @Override
    public Class<?> getGenericClass() {
        return Void.class;
    }

    @Override
    public Class<?> getArrayGenericClass() {
        return Void[].class;
    }
    
    public static class UrlValues{
        public String deviceId;
        public String appName;
        /* /1/appname/asbd */
        public UrlValues(String url){
            try{
                String[] vals = url.split("/");
                deviceId = vals[1];
                appName = vals[2];
            }catch(Exception e){
                throw new IllegalArgumentException("Invalid Url: " + url + ",expected /{id}/{appname}");
                
            }
        }
    }
}
