package com.scurab.gwt.rlw.server.data;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.server.Queries;
import com.scurab.gwt.rlw.shared.QueryNames;
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class DataProviderTest extends ApplicationTest {

    private static final String TEST_APP2 = "TestApp2";
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
        List<LogItem> logs = dp.getLogs(createParams(0, TEST_APP2));
        assertNotNull(logs);
        assertTrue(logs.size() > 0);
    }    
    
    @Test 
    public void getPlatforms(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_PLATFORMS, null);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getPlatformsByAppName(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_PLATFORMS_BY_APPNAME, TEST_APP2);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getBrands(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_BRANDS, null);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getBrandsByAppName(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_BRANDS_BY_APPNAME, TEST_APP2);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getResolutions(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_RESOLUTIONS, null);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getResolutionsByAppName(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_RESOLUTIONS_BY_APPNAME, TEST_APP2);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getDataTypes(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_DATATYPES, null);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getDataTypesByAppName(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_DATATYPES_BY_APPNAME, TEST_APP2);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getCategories(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_CATEGORIES, null);
        assertNotNull(l);
        assertTrue(l.size() > 0);
    }
    
    @Test 
    public void getCategoriesByAppName(){
        DataProvider dp = new DataProvider();
        List<String> l = dp.getDistinctValues(QueryNames.SELECT_CATEGORIES_BY_APPNAME, TEST_APP2);
        assertNotNull(l);
        assertTrue(l.size() > 0);
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
