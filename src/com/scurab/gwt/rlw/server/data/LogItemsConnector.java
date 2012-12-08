package com.scurab.gwt.rlw.server.data;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.LogItemRespond;
import com.scurab.gwt.rlw.shared.model.Respond;

public class LogItemsConnector extends Connector {

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
            Respond r = new LogItemRespond(e);
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
            LogItem[] saved = onPost(json, json.charAt(0) == '[');
            if (saved.length == 0) {
                response = mGson.toJson(new LogItemRespond("Nothing saved!?"));
            } else if (saved.length == 1) {
                response = mGson.toJson(new LogItemRespond("OK", saved[0]));
            } else {
                response = mGson.toJson(new LogItemRespond("OK Saved:" + saved.length));
            }
        } catch (Exception e) {
            Respond r = new LogItemRespond(e);
            response = mGson.toJson(r);
        } finally {
            resp.getOutputStream().write(response.getBytes());
            resp.getOutputStream().close();
        }
    }

    private LogItem[] onPost(String obj, boolean isArray) {
        LogItem[] toWrite = null;
        if (isArray) {
            toWrite = mGson.fromJson(obj, LogItem[].class);
        } else {
            LogItem d = mGson.fromJson(obj, LogItem.class);
            toWrite = new LogItem[] { d };
        }
        onWrite(toWrite);
        return toWrite;
    }

    protected void onWrite(LogItem[] data) {
        Session s = Database.openSession();
        s.beginTransaction();
        for (LogItem d : data) {
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
            Respond r = new LogItemRespond(e);
            response = mGson.toJson(r);
        } finally {
            resp.getOutputStream().write(response.getBytes());
            resp.getOutputStream().close();
        }
    }
}
