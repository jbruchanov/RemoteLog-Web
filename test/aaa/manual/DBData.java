package aaa.manual;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.server.util.DataGenerator;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

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
    }
}
