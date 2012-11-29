package com.scurab.gwt.rlw.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.scurab.gwt.rlw.shared.model.Device;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface DataServiceAsync {
    void getDevices(AsyncCallback<List<Device>> data);

    void getDevice(int id, AsyncCallback<Device> callback);    
}
