package com.scurab.gwt.rlw.server.data;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.shared.model.Respond;

public class Connector extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6167991750744476029L;
    protected final Gson mGson = Application.GSON;
    
    protected void doNotImplementedRequest(HttpServletRequest req, HttpServletResponse resp){
        try{
            Respond<?> respond = null;        
            respond = new Respond<Void>(new Exception("Not implemented request"));    
            String result = mGson.toJson(respond);
            resp.getOutputStream().write(result.getBytes());
            resp.getOutputStream().close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    protected void doNotImplementedRequest(String method, HttpServletRequest req, HttpServletResponse resp){
        try{
            Respond<?> respond = null;        
            respond = new Respond<Void>(new Exception("Not implemented request:" + method));    
            String result = mGson.toJson(respond);
            resp.getOutputStream().write(result.getBytes());
            resp.getOutputStream().close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
