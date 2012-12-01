package com.scurab.gwt.rlw.server.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.scurab.gwt.rlw.client.DataService;
import com.scurab.gwt.rlw.server.util.DataGenerator;
import com.scurab.gwt.rlw.shared.model.Device;

public class DataServiceImpl extends RemoteServiceServlet implements DataService {

    /**
     * 
     */
    private static final long serialVersionUID = -4187117132460121962L;

    @Override
    public List<Device> getDevices() {
        return DataGenerator.genDevices(150);
    }

    @Override
    public Device getDevice(int id) {
        return null;
    }

    @Override
    public List<String> getApplications() {
        // String[] apps = new String[] { "RadioAlarm", "RemoteControl", "ZumpaReader" };
        // return Arrays.asList(apps);
        List<String> apps = new ArrayList<String>();
        for(int i = 0;i<100;i++){
            apps.add("Applikaèka " + i);
        }
        return apps;
    }

}
