package com.scurab.gwt.rlw.server.data;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.DeviceRespond;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.LogItemRespond;
import com.scurab.gwt.rlw.shared.model.Respond;

public class LogItemsConnector extends Connector<LogItem> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected Respond<?> onRequest(InputStream is) throws Exception {
        LogItemRespond response = null;
        String json = read(is);
        LogItem[] data = parse(json, json.charAt(0) == '[');
        LogItem[] saved = onWrite(data);
        if (saved.length == 0) {
            response = new LogItemRespond("Nothing saved!?");
        } else if (saved.length == 1) {
            response = new LogItemRespond(saved[0]);
        } else {
            response = new LogItemRespond();
        }
        return response;
    }

    
    @Override
    protected LogItem[] parse(String obj, boolean isArray) {
        LogItem[] toWrite = null;
        if (isArray) {
            toWrite = mGson.fromJson(obj, LogItem[].class);
        } else {
            LogItem d = mGson.fromJson(obj, LogItem.class);
            toWrite = new LogItem[] { d };
        }
        return toWrite;
    }
}
