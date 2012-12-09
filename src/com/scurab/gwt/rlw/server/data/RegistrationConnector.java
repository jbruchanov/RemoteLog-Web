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

public class RegistrationConnector extends Connector<Device> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
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

    @Override
    public Class<?> getGenericClass() {
        return Device.class;
    }

    @Override
    public Class<?> getArrayGenericClass() {
        return Device[].class;
    }
}
