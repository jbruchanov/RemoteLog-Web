package com.scurab.gwt.rlw.server.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServlet;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.scurab.gwt.rlw.server.Application;

public abstract class Connector extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1009792762610851131L;

    protected final Gson mGson = Application.GSON;

    protected String read(InputStream is) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "utf-8");
        String theString = writer.toString();
        return theString;
    }
}
