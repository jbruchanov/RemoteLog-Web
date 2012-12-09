package com.scurab.gwt.rlw.server.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.scurab.gwt.rlw.client.DataService;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class DataServiceImpl extends RemoteServiceServlet implements DataService {

    /**
     * 
     */
    private static final long serialVersionUID = -4187117132460121962L;

    @Override
    public List<String> getApplications() throws Exception {
        return new DataProvider().getApplications();
    }

    @Override
    public List<Device> getDevices(String jsonParams) {
        return new DataProvider().getDevices(parseParams(jsonParams));
    }

    @Override
    public List<LogItem> getLogs(String jsonParams) {
        return new DataProvider().getLogs(parseParams(jsonParams));
    }

    @Override
    public List<String> getDistinctValues(String appName, String query) {
        return new DataProvider().getDistinctValues(query, appName);
    }
    
    /**
     * Parse data and try to fix dates 
     * @param json
     * @return
     */
    private HashMap<String, Object> parseParams(String json){
        HashMap<String, Object> params = Application.GSON.fromJson(json, HashMap.class);
        for(String key : params.keySet()){
            if(key.toLowerCase().contains("date")){
                Object o = params.get(key);
                if(o != null){
                    String v = String.valueOf(o);
                    try{
                        Date d = Application.DATEFORMAT.parse(v);
                        params.put(key, d);
                    }catch(Exception e){
                        //ignore it
                    }
                }
            }
        }
        return params;
    }

}
