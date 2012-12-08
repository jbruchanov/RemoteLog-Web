package com.scurab.gwt.rlw.server.data;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class DataProviderTest extends ApplicationTest {

    @Test
    public void testGetLogs() {
        DataProvider dp = new DataProvider();
        List<LogItem> logs = dp.getLogs(createParams(0));
        assertNotNull(logs);
        assertTrue(logs.size() > 0);
    }    
    
    @Test
    public void testGetLogsByApp() {
        DataProvider dp = new DataProvider();
        List<LogItem> logs = dp.getLogs(createParams(0, "TestApp2"));
        assertNotNull(logs);
        assertTrue(logs.size() > 0);
    }
    
    private static HashMap<String, Object> createParams(int page){
        return createParams(page, null);
    }
    private static HashMap<String, Object> createParams(int page, String appName){
        HashMap<String, Object> v = new HashMap<String, Object>();
        v.put(SharedParams.PAGE, page);
        if(appName != null){
            v.put(SharedParams.APP_NAME, appName);
        }
        return v;
    }

}
