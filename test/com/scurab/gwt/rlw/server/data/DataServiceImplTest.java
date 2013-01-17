package com.scurab.gwt.rlw.server.data;

import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;

public class DataServiceImplTest extends ApplicationTest {

    @Test
    public void testA(){
        DataServiceImpl dsi = new DataServiceImpl();
        dsi.getLogs("{}");
    }
    // @Test
    // public void testGetDevices() {
    // DataServiceImpl dsi = new DataServiceImpl();
    // List<Device> d = dsi.getDevices(new HashMap<String, Object>());
    // assertNotNull(d);
    // assertTrue(d.size() > 0);
    // }
    //
    // @Test
    // public void testGetDevicesValue() {
    // DataServiceImpl dsi = new DataServiceImpl();
    // HashMap<String,Object> params = new HashMap<String, Object>();
    // params.put(SharedParams.APP_NAME, "TestApp2");
    // params.put(SharedParams.PAGE, 0);
    // List<Device> d = dsi.getDevices(params);
    // assertNotNull(d);
    // assertTrue(d.size() > 0);
    // }
    
}
