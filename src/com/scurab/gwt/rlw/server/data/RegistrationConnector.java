package com.scurab.gwt.rlw.server.data;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gwt.dev.shell.remoteui.RemoteMessageProto.Message.Response;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.DeviceRespond;
import com.scurab.gwt.rlw.shared.model.Respond;

public class RegistrationConnector extends Connector {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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
    
    protected DeviceRespond onRequest(InputStream is) throws IOException{
        DeviceRespond response = null;
        String json = read(is);
        Device[] data = parse(json, json.charAt(0) == '[');
        Device[] saved = onWrite(data);
        if (saved.length == 0) {
            response = new DeviceRespond("Nothing saved!?");
        } else if (saved.length == 1) {
            response = new DeviceRespond(saved[0]);
        } else {
            response = new DeviceRespond();
        }
        return response;
    }
    
    private Device[] parse(String obj, boolean isArray){
        Device[] toWrite = null;
        if (isArray) {
            toWrite = mGson.fromJson(obj, Device[].class);
        } else {
            Device d = mGson.fromJson(obj, Device.class);
            toWrite = new Device[] { d };
        }
        return toWrite;
    }


    protected Device[] onWrite(Device[] data) {
        Session s = Database.openSession();
        s.beginTransaction();
        for (Device d : data) {            
            s.saveOrUpdate(d);
        }
        s.getTransaction().commit();
        return data;
    }

    
}
