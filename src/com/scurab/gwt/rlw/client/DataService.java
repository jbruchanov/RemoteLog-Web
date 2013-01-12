package com.scurab.gwt.rlw.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRespond;
import com.scurab.gwt.rlw.shared.model.Settings;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("dataservice")
public interface DataService extends RemoteService {
    List<String> getApplications() throws Exception;

    /**
     * 
     * @param appName
     *            optional
     * @return
     */
    List<String> getDistinctValues(String appName, String query);

    List<Device> getDevices(String jsonParams);

    List<LogItem> getLogs(String jsonParams);

    String getProperties();
    
    PushMessage[] getPushMessages();
    
    PushMessageRespond sendMessage(String json);
    
    Settings getSettings(String jsonParams);
    
    Settings saveSettings(String jsonParams);
    
    /**
     * Delete any custom device settings for app
     * @param appName
     */
    int deleteDeviceSettings(String appName);
}
