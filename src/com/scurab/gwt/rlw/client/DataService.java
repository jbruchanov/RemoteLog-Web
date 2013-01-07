package com.scurab.gwt.rlw.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
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
    
    /**
     * Can return 2 items if there is definition for global app and device<br/>
     * First value is always global, lastone is device specific
     * 
     * By default stupid mysql behaviour which ignores NULL unique value, you can get more than 2 value,
     * if there is an inconsistency in DB => last one will be device specific
     * 
     * @param appName
     * @param deviceId with null returns global app settings
     * @return
     */
    Settings[] getSettings(String jsonParams);
    
    /**
     * 
     * @param appName
     * @param deviceId if null it's saved like global settings
     * @param settings
     * @return saved value
     */
    Settings saveSettings(String jsonParams);
}
