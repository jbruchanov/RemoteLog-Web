package com.scurab.gwt.rlw.server.data;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.shared.model.Device;

public class DataServiceImplTest extends ApplicationTest {

    @Test
    public void testGetDevices() {
        DataServiceImpl dsi = new DataServiceImpl();
        List<Device> d = dsi.getDevices(null, 0);
        assertNotNull(d);
        assertTrue(d.size() > 0);
    }

    @Test
    public void testGetDevicesValue() {
        DataServiceImpl dsi = new DataServiceImpl();
        List<Device> d = dsi.getDevices("TestApp", 0);
        assertNotNull(d);
        assertTrue(d.size() > 0);
    }

    @Test
    public void testDB() {
        Session s = Database.openSession();
        Query q = null;
        q = s.createQuery("FROM Devices INNER JOIN LogItems");
        List<Device> a = q.list();
        assertTrue(a.get(0) instanceof Device);
        s.close();
    }

}
