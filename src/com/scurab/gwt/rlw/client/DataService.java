package com.scurab.gwt.rlw.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageResponse;
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
    
    PushMessageResponse sendMessage(PushMessageRequest json);
    
    Settings getSettings(String jsonParams);
    
    Settings saveSettings(Settings settings);
    
    /**
     * Delete any custom device settings for app
     * @param appName
     */
    int deleteDeviceSettings(String appName);
    
    
    /**
     * Stupid GWT policy
     */
    Settings policySettings();
    LogItem policyLogItem();
    Device policyDevice();
}
