package com.scurab.gwt.rlw.server.data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.JsonObject;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;
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
            throw new Exception("Not implemented");
        } catch (Exception e) {
            Respond r = new Respond(e);
            response = mGson.toJson(r);
        } finally {
            resp.getOutputStream().write(response.getBytes());
            resp.getOutputStream().close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = null;
        try {
            String json = read(req.getInputStream());
            Device[] saved = onPost(json, json.charAt(0) == '[');
            if (saved.length == 0) {
                response = mGson.toJson(new Respond("Nothing saved!?"));
            } else if (saved.length == 1) {
                response = mGson.toJson(new Respond<Device>(saved[0]));
            } else {
                response = mGson.toJson(new Respond<LogItem>());
            }
        } catch (Exception e) {
            Respond r = new Respond(e);
            response = mGson.toJson(r);
        } finally {
            resp.getOutputStream().write(response.getBytes());
            resp.getOutputStream().close();
        }
    }

    private Device[] onPost(String obj, boolean isArray) {
        Device[] toWrite = null;
        if (isArray) {
            toWrite = mGson.fromJson(obj, Device[].class);
        } else {
            Device d = mGson.fromJson(obj, Device.class);
            toWrite = new Device[] { d };
        }
        onWrite(toWrite);
        return toWrite;
    }

    protected void onWrite(Device[] data) {
        Session s = Database.openSession();
        s.beginTransaction();
        for (Device d : data) {
            s.save(d);
        }
        s.getTransaction().commit();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String response = null;
        try {
            throw new Exception("Not implemented");
        } catch (Exception e) {
            Respond r = new Respond(e);
            response = mGson.toJson(r);
        } finally {
            resp.getOutputStream().write(response.getBytes());
            resp.getOutputStream().close();
        }
    }
}
