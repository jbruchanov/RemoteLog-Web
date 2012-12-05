package com.scurab.gwt.rlw.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DataServiceAsync {
    void getDevices(String app, int page, AsyncCallback<List<Device>> data);

    void getDevice(int id, AsyncCallback<Device> callback);

    void getApplications(AsyncCallback<List<String>> callback);

    void getLogs(String app, int page, AsyncCallback<List<LogItem>> callback);
}
