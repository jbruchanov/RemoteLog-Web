package com.scurab.gwt.rlw.server.data;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Connector extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1009792762610851131L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getOutputStream().write("doGet".getBytes());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonRequest = read(req.getInputStream());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getOutputStream().write("doPut".getBytes());
    }

    public static String read(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            sb.append(new String(data, 0, nRead));
        }
        return sb.toString();
    }

}
