package com.scurab.gwt.rlw.server.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.shared.model.Device;

public class DataServiceImplTest extends ApplicationTest {

    @Test
    public void testGetDevices() {
        DataServiceImpl dsi = new DataServiceImpl();
        List<Device> d = dsi.getDevices(null, 0);
        assertNotNull(d);
        assertTrue(d.size() > 0);
    }

}
