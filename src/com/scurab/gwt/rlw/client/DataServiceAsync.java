package com.scurab.gwt.rlw.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageResponse;
import com.scurab.gwt.rlw.shared.model.Settings;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DataServiceAsync {

    void getApplications(AsyncCallback<List<String>> callback);

    void getDevices(String jsonParams, AsyncCallback<List<Device>> callback);

    void getLogs(String jsonParams, AsyncCallback<List<LogItem>> callback);

    void getDistinctValues(String appName, String query, AsyncCallback<List<String>> callback);

    void getProperties(AsyncCallback<String> callback);

    void getPushMessages(AsyncCallback<PushMessage[]> callback);

    void sendMessage(PushMessageRequest json, AsyncCallback<PushMessageResponse> callback);

    void getSettings(String jsonParams, AsyncCallback<Settings> callback);

    void saveSettings(Settings settings, AsyncCallback<Settings> callback);

    void deleteDeviceSettings(String appName, AsyncCallback<Integer> callback);

    void policyLogItem(AsyncCallback<LogItem> callback);

    void policySettings(AsyncCallback<Settings> callback);

    void policyDevice(AsyncCallback<Device> callback);

}
