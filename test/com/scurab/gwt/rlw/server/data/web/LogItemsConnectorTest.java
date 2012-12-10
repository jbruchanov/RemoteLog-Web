package com.scurab.gwt.rlw.server.data.web;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

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

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.data.Database;
import com.scurab.gwt.rlw.server.data.MockServletInputStream;
import com.scurab.gwt.rlw.server.data.MockServletOutputStream;
import com.scurab.gwt.rlw.server.data.web.LogItemsConnector;
import com.scurab.gwt.rlw.server.util.DataGenerator;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.LogItemRespond;

public class LogItemsConnectorTest extends ApplicationTest {

    @Test
    public void testDoPostHttpServletRequestHttpServletResponseMoreItems() throws IOException, ServletException {
        LogItem[] data = new LogItem[] { DataGenerator.genRandomLogItem(), DataGenerator.genRandomLogItem() };
        final String reqJson = Application.GSON.toJson(data);

        // prepare mock objects
        LogItemsConnector rc = new LogItemsConnector();
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

        LogItemRespond r = Application.GSON.fromJson(httpResponse, LogItemRespond.class);

        assertTrue(r.getMessage().startsWith("OK"));
        assertNull(r.getContext());

        Session s = Database.openSession();
        Query q = s.createQuery(String.format("FROM LogItems"));
        // test if is somethink saved
        assertTrue(q.list().size() >= 2);
        List l = q.list();
        // delete it
        s.beginTransaction();
        for (int i = 0, n = l.size(); i < n; i++) {
            s.delete(l.get(i));
        }
        s.getTransaction().commit();
        s.close();
    }

    @Test
    public void testDoPostHttpServletRequestHttpServletResponseOneItem() throws IOException, ServletException {
        LogItem d = DataGenerator.genRandomLogItem();
        final String reqJson = Application.GSON.toJson(d);

        // prepare mock objects
        LogItemsConnector rc = new LogItemsConnector();
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

        LogItemRespond r = Application.GSON.fromJson(httpResponse, LogItemRespond.class);

        assertEquals("OK", r.getMessage());
        assertNotNull(r.getContext());
        LogItem li = r.getContext();
        assertTrue(li.getID() > 0);

        Session s = Database.openSession();
        Query q = s.createQuery(String.format("FROM LogItems"));
        // test if is somethink saved
        assertTrue(q.list().size() >= 1);
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
