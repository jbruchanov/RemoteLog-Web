package com.scurab.gwt.rlw.server.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;

import com.google.gson.Gson;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.DeviceRespond;
import com.scurab.gwt.rlw.shared.model.Respond;

public abstract class Connector<T> extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1009792762610851131L;

    protected final Gson mGson = Application.GSON;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = null;
        try {            
            throw new Exception("Get Method not implemented");
        } catch (Exception e) {
            Respond<?> r = new DeviceRespond(e);
            response = mGson.toJson(r);
        } finally {
            resp.getOutputStream().write(response.getBytes());
            resp.getOutputStream().close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Respond<?> respond = null;
        try {
            respond = onRequest(req.getInputStream());             
        } catch (Exception e) {
            respond = new DeviceRespond(e);
            
        } finally {
            String result = mGson.toJson(respond);
            resp.getOutputStream().write(result.getBytes());
            resp.getOutputStream().close();
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = null;
        try {            
            throw new Exception("Get Method not implemented");
        } catch (Exception e) {
            Respond<?> r = new DeviceRespond(e);
            response = mGson.toJson(r);
        } finally {
            resp.getOutputStream().write(response.getBytes());
            resp.getOutputStream().close();
        }
    }
    
    protected abstract Respond<?> onRequest(InputStream is) throws Exception;
    
    @SuppressWarnings("unchecked")
    protected abstract T[] parse(String obj, boolean isArray);        

    protected T[] onWrite(T[] data) {
        Session s = Database.openSession();
        s.beginTransaction();
        for (T d : data) {            
            s.saveOrUpdate(d);
        }
        s.getTransaction().commit();
        return data;
    }
    
    protected String read(InputStream is) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "utf-8");
        String theString = writer.toString();
        return theString;
    }
    
}
