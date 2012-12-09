package com.scurab.gwt.rlw.server.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.server.util.DataGenerator;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.DeviceRespond;

public class RegistrationConnectorTest extends ApplicationTest {

    private Gson mGson = new Gson();

    @Test
    public void testDoPostHttpServletRequestHttpServletResponseOneItem() throws ServletException, IOException {
        Device d = DataGenerator.genDevices(1).get(0);
        final String reqJson = mGson.toJson(d);

        // prepare mock objects
        RegistrationConnector rc = new RegistrationConnector();
        HttpServletRequest req = mock(HttpServletRequest.class);
        doAnswer(new Answer<InputStream>() {
            @Override
            public InputStream answer(InvocationOnMock invocation) throws Throwable {
                return new MockServletInputStream(reqJson.getBytes());
            }
        }).when(req).getInputStream();

        final MockServletOutputStream msos = new MockServletOutputStream();
        HttpServletResponse resp = mock(HttpServletResponse.class);
        doAnswer(new Answer<OutputStream>() {

            @Override
            public OutputStream answer(InvocationOnMock invocation) throws Throwable {
                return msos;
            }
        }).when(resp).getOutputStream();

        // post
        rc.doPost(req, resp);

        String httpResponse = msos.getStringValue();

        // test
        assertNotNull(httpResponse);

        DeviceRespond r = mGson.fromJson(httpResponse, DeviceRespond.class);

        assertEquals("OK", r.getMessage());
        assertNotNull(r.getContext());

        Session s = Database.openSession();
        Query q = s.createQuery(String.format("FROM Devices"));
        // test if is somethink saved
        assertTrue(q.list().size() > 0);
        List l = q.list();
        // delete it
        s.beginTransaction();
        for (int i = 0, n = l.size(); i < n; i++) {
            s.delete(l.get(i));
        }
        s.getTransaction().commit();
        s.close();
    }
}
