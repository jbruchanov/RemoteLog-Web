package com.scurab.gwt.rlw.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("dataservice")
public interface DataService extends RemoteService {
    List<String> getApplications() throws Exception;

    List<Device> getDevices(String jsonParams);

    List<LogItem> getLogs(String jsonParams);
}
