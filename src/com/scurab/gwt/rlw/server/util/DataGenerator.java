package com.scurab.gwt.rlw.server.util;

import java.util.ArrayList;
import java.util.List;

import com.scurab.gwt.rlw.shared.model.Device;

public class DataGenerator {
    
    public static List<Device> genDevices(int howMany){
	ArrayList<Device> result = new ArrayList<Device>();
	for(int i = 0;i<howMany;i++){
	    Device d = new Device();
	    d.setId(i);
	    d.setPlatform("Android");
	    d.setDescription("Description" + i);
	    result.add(d);
	}
	return result;
    }
}
