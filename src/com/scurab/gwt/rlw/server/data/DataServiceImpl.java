package com.scurab.gwt.rlw.server.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.scurab.gwt.rlw.client.DataService;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.push.AndroidSender;
import com.scurab.gwt.rlw.shared.TableColumns;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageRespond;
import com.scurab.gwt.rlw.shared.model.Settings;

/**
 * AJAX data service implementation
 * @author Joe Scurab
 *
 */
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
     * 
     * @param json
     * @return
     */
    private HashMap<String, Object> parseParams(String json) {
        HashMap<String, Object> params = Application.GSON.fromJson(json, HashMap.class);
        for (String key : params.keySet()) {
            if (key.toLowerCase().contains("date")) {
                Object o = params.get(key);
                if (o != null) {
                    String v = String.valueOf(o);
                    try {
                        Date d = Application.DATEFORMAT.parse(v);
                        params.put(key, d);
                    } catch (Exception e) {
                        // ignore it
                    }
                }
            }
        }
        return params;
    }

    @Override
    public String getProperties() {
        System.out.println("getProperties()");
        String json =  Application.GSON.toJson(Application.CLIENT_PROPERTIES);
        System.out.println("Got:" + json);
        return json;
    }

    @Override
    public PushMessage[] getPushMessages() {
        return Application.PUSH_MESSAGES;
    }

    @Override
    public PushMessageRespond sendMessage(String json) {
        PushMessageRequest req = Application.GSON.fromJson(json, PushMessageRequest.class);
        PushMessageRespond res = null;
        String platform = req.getDevicePlatform().toLowerCase();
        if(platform.startsWith("android")){
            res = AndroidSender.send(req);
        }else{
            throw new IllegalStateException(String.format("Platform '%s' not implemented!", platform));
        }
        return res;
    }

    @Override
    public Settings getSettings(String jsonParams) {
        HashMap<String, Object> params = Application.GSON.fromJson(jsonParams, HashMap.class);
        
        String appName = (String) params.get(TableColumns.SettingsAppName);
        Integer deviceId = null;
        if(params.containsKey(TableColumns.SettingsDeviceID)){
            deviceId = ((Number)params.get(TableColumns.SettingsDeviceID)).intValue();
        }
        if(appName == null){
            throw new IllegalArgumentException("Missing " + TableColumns.SettingsAppName);
        }
        return new DataProvider().getSettings(appName, deviceId);
    }

    @Override
    public Settings saveSettings(String jsonParams) {
        Settings s = Application.GSON.fromJson(jsonParams, Settings.class);
        
        if(s.getAppName() == null){
            throw new IllegalArgumentException("appName is null!");
        }
        
        return new DataProvider().saveSettings(s);
    }

    @Override
    public int deleteDeviceSettings(String appName) {
        return new DataProvider().deleteDeviceSpecificSettings(appName);
    }
}
