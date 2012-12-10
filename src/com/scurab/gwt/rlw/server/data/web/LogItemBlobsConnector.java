package com.scurab.gwt.rlw.server.data.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.scurab.gwt.rlw.server.data.Database;
import com.scurab.gwt.rlw.shared.model.LogItem;
import com.scurab.gwt.rlw.shared.model.LogItemBlob;

public class LogItemBlobsConnector extends DataConnector<Void> {

    private static final String FILE_NAME_NOT_DEFINED = "FileNameNotDefined.bin";
    private static final String ATTACHMENT_TEMPLATE = "attachment; filename=\"%s";
    private static final String CONTENT_DISPOSITION = "Content-disposition";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String OCTET_STREAM = "application/octet-stream";
    /**
     * 
     */
    private static final long serialVersionUID = 3072901058334309712L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        
        String sid = req.getPathInfo().replaceFirst("/", "");
        Session s = null;
        try{
            s = Database.openSession();
            sid = req.getPathInfo().replaceFirst("/", "");
            int id = Integer.parseInt(sid);
            onGet(s, id, resp);
        }
        catch(NumberFormatException nfe){
            resp.getOutputStream().write(("Invalid logitemid:" + sid).getBytes());
        }
        catch(Exception e){
            resp.getOutputStream().write(e.getMessage().getBytes());
        }
        finally{
            if(s != null && s.isOpen()){
                s.close();
            }
        }
        resp.getOutputStream().close();
    }
    
    protected void onGet(Session s, int id, HttpServletResponse resp) throws IOException{
        //get data from db
        LogItem li = (LogItem) s.get(LogItem.class, id);
        LogItemBlob lib = (LogItemBlob) s.get(LogItemBlob.class, id);
        byte[] data = lib.getData();       
        
        //double check mime
        String mime = li.getBlobMime().toLowerCase();
        if(mime == null || mime.length() == 0){
            mime = OCTET_STREAM;
        }
        
        //double check for fileName
        String fileName = lib.getFileName();
        if(fileName == null || fileName.length() == 0){
            fileName = FILE_NAME_NOT_DEFINED;
        }
        
        if(mime.startsWith(IMAGE)){
            //keep browser to show it
        }else if(mime.startsWith(TEXT)){
            //keep browser to show it
        }else{
            resp.setHeader(CONTENT_DISPOSITION,String.format(ATTACHMENT_TEMPLATE, lib.getFileName()));
        }
        //write data
        resp.setContentType(li.getBlobMime());
        resp.setContentLength(data.length);
        resp.getOutputStream().write(data);
        resp.getOutputStream().close();
    }    

    @Override
    public Class<?> getGenericClass() {
        return Void.class;
    }

    @Override
    public Class<?> getArrayGenericClass() {
        return Void[].class;
    }
}
