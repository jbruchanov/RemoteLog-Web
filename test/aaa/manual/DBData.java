package aaa.manual;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.server.data.DataProvider;
import com.scurab.gwt.rlw.server.data.Database;
import com.scurab.gwt.rlw.server.util.DataGenerator;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.Settings;

public class DBData extends ApplicationTest {

    @Test
    public void genData() {
        List<Device> devs = DataGenerator.genDevices(10);
        Session s = Database.openSession();
        s.beginTransaction();
        for (int i = 0, n = devs.size(); i < n; i++) {
            Device d = devs.get(i);
            s.saveOrUpdate(devs.get(i));
        }
        s.getTransaction().commit();

        s.beginTransaction();
        for (int i = 0, n = devs.size(); i < n; i++) {
            Device d = devs.get(i);
            List<LogItem> li = DataGenerator.genRandomLogItems(d.getDeviceID(), "TestApp2", 150);
            for (int j = 0, m = li.size(); j < m; j++) {
                s.saveOrUpdate(li.get(j));
            }
        }
        s.getTransaction().commit();

        List<Device> devs2 = DataGenerator.genDevices(10);
        s.beginTransaction();
        for (int i = 0, n = devs.size(); i < n; i++) {
            Device d = devs2.get(i);
            s.saveOrUpdate(d);
        }
        devs.addAll(devs2);
        for (int i = 0, n = devs.size(); i < n; i++) {
            Device d = devs.get(i);
            List<LogItem> li = DataGenerator.genRandomLogItems(d.getDeviceID(), "TestApp", 150);
            for (int j = 0, m = li.size(); j < m; j++) {
                s.saveOrUpdate(li.get(j));
            }
        }
        s.getTransaction().commit();
        
        s.close();
    }

    @Test
    public void genDevs() {
        List<Device> devs = DataGenerator.genDevices(150);
        Session s = Database.openSession();
        s.beginTransaction();
        for (int i = 0, n = devs.size(); i < n; i++) {
            Device d = devs.get(i);
            s.saveOrUpdate(devs.get(i));
        }
        s.getTransaction().commit();
        
        s.close();
    }
    
    @Test
    public void genSettings(){
        Session s = Database.openSession();
        s.createSQLQuery("DELETE FROM Settings").executeUpdate();
        s.beginTransaction();
        Settings s1 = DataGenerator.getRandomSettings(null, "TestApp1");
        Settings s2 = DataGenerator.getRandomSettings(null, "TestApp2");
        s.saveOrUpdate(s1);
        s.saveOrUpdate(s2);
        s.getTransaction().commit();
        s.close();
        
        
        Settings sets = new DataProvider().getSettings("TestApp1", null);
        assertNotNull(sets);
        assertEquals(s1.getJsonValue(),sets.getJsonValue());
        
        sets = new DataProvider().getSettings("TestApp2", null);
        assertNotNull(sets);
        assertEquals(s2.getJsonValue(),sets.getJsonValue());
        
    }
    
    @Test
    public void genSettingsForDevices(){
        
        List<Device> devs = DataGenerator.genDevices(2);
        Session s = Database.openSession();
        
        s.createSQLQuery("DELETE FROM Settings").executeUpdate();
        s.beginTransaction();
        for (int i = 0, n = devs.size(); i < n; i++) {
            Device d = devs.get(i);
            s.saveOrUpdate(devs.get(i));
        }
        
        Settings s0 = DataGenerator.getRandomSettings(null, "TestApp1");
        Settings s1 = DataGenerator.getRandomSettings(devs.get(0).getDeviceID(), "TestApp1");
        Settings s2 = DataGenerator.getRandomSettings(devs.get(1).getDeviceID(), "TestApp1");
        Settings s3 = DataGenerator.getRandomSettings(devs.get(0).getDeviceID(), "TestApp3");
        
        s.getTransaction().commit();
        
        s.beginTransaction();
        s.saveOrUpdate(s0);
        s.saveOrUpdate(s1);
        s.saveOrUpdate(s2);  
        s.saveOrUpdate(s3);
        s.getTransaction().commit();
        s.close();
       
        //test both of them
        Settings sets;// = new DataProvider().getSettings("TestApp1", devs.get(0).getDeviceID());
//        assertNotNull(sets);
//        assertEquals(2,sets.length);
//        assertEquals(s0.getJsonValue(), sets[0].getJsonValue());
//        assertEquals(s1.getJsonValue(), sets[1].getJsonValue());
        
        //test just global
        sets = new DataProvider().getSettings("TestApp1", null);
        assertNotNull(sets);
        assertEquals(s0.getJsonValue(),sets.getJsonValue());
        
        //test just for device, where is no global prefs
        sets = new DataProvider().getSettings("TestApp3", devs.get(0).getDeviceID());
        assertNotNull(sets);
        assertEquals(s3.getJsonValue(),sets.getJsonValue());
    }    
}
