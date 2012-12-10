package com.scurab.gwt.rlw.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DataServiceAsync {

    void getApplications(AsyncCallback<List<String>> callback);

    void getDevices(String jsonParams, AsyncCallback<List<Device>> callback);

    void getLogs(String jsonParams, AsyncCallback<List<LogItem>> callback);

    void getDistinctValues(String appName, String query, AsyncCallback<List<String>> callback);

    void getProperties(AsyncCallback<String> callback);
}
