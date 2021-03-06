package com.scurab.gwt.rlw.server.data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.scurab.gwt.rlw.client.DataService;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.push.AndroidSender;
import com.scurab.gwt.rlw.server.push.WinPhoneSender;
import com.scurab.gwt.rlw.shared.TableColumns;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageResponse;
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
        List<Device> items = new DataProvider().getDevices(parseParams(jsonParams));
        for(Device li : items){
            //fill data for client
            Date d = li.getCreated();
            if(d != null){
                li.setCreatedText(Application.DATEFORMAT.format(d));
            }
            d = li.getUpdated();
            if(d != null){
                li.setUpdatedText(Application.DATEFORMAT.format(d));
            }
        }
        return items;
    }

    @Override
    public List<LogItem> getLogs(String jsonParams) {
        List<LogItem> items = new DataProvider().getLogs(parseParams(jsonParams));
        for(LogItem li : items){
            //fill data for client
            li.setDateText(Application.DATEFORMAT.format(li.getDate()));
        }
        return items;
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
        HashMap<String, Object> params = Application.fromJson(json, HashMap.class);
        for (String key : params.keySet()) {
            if (canBeDate(key)) {
                Object o = params.get(key);
                if (o != null) {
                    String v = String.valueOf(o);
                    try {
                        Date d = Application.DATEFORMAT.parse(v);
                        params.put(key, d);
                    } catch (Exception e) {
                        // ignore it and keep default value
                    }
                }
            }
        }
        return params;
    }
    
    private boolean canBeDate(String key){
        key = key.toLowerCase();
        return key.contains("date") || key.contains("register") || key.contains("udpate") || key.contains("create");
    }

    @Override
    public String getProperties() {
        return Application.toJson(Application.CLIENT_PROPERTIES);
    }

    @Override
    public PushMessage[] getPushMessages() {
        return Application.PUSH_MESSAGES;
    }

    @Override
    public PushMessageResponse sendMessage(PushMessageRequest json) {
        PushMessageRequest req = json;//.GSON.fromJson(json, PushMessageRequest.class);
        PushMessageResponse res = null;
        String platform = req.getDevicePlatform().toLowerCase();
        if(platform.startsWith("android")){
            res = AndroidSender.send(req);
        }else if(platform.startsWith("windowsphone")){
            res = WinPhoneSender.send(req);
        }else{
            throw new IllegalStateException(String.format("Platform '%s' not implemented!", platform));
        }
        return res;
    }

    @Override
    public Settings getSettings(String jsonParams) {
        HashMap<String, Object> params = Application.fromJson(jsonParams, HashMap.class);
        
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
    public Settings saveSettings(Settings s) {
//        Settings s = Application.GSON.fromJson(jsonParams, Settings.class);
        
        if(s.getAppName() == null){
            throw new IllegalArgumentException("appName is null!");
        }
        
        return new DataProvider().saveSettings(s);
    }

    @Override
    public int deleteDeviceSettings(String appName) {
        return new DataProvider().deleteDeviceSpecificSettings(appName);
    }

    /**
     * Ignore this to set policy GWT stupidity
     */
    @Override
    public Settings policySettings() {
        return null;
    }

    @Override
    public LogItem policyLogItem() {
        return null;
    }

    @Override
    public Device policyDevice() {
        return null;
    }
}
