package com.scurab.gwt.rlw.server.push;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.push.android.Message;
import com.scurab.gwt.rlw.server.push.android.Result;
import com.scurab.gwt.rlw.server.push.android.Sender;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageRespond;

public class AndroidSender{

    private static final Sender mSender;
    
    static{
        String id = Application.APP_PROPS.getProperty("push_android");
        if(id != null && id.length() > 0){
            mSender = new Sender(id);
        }else{
            throw new IllegalStateException("push_android key not found in remotelogweb.properties!");
        }
    }
    
    
    public static PushMessageRespond send(PushMessageRequest req) {
        final long timestamp = System.currentTimeMillis();
        Result result = null;
        Exception err = null;
        
        try
        {
            Message m = getMessage(timestamp, req);
            String token = req.getPushToken();
            result = mSender.sendNoRetry(m, token);
        
        }catch(Exception e){
            err = e;
            e.printStackTrace();
        }
        
        PushMessageRespond pmr = new PushMessageRespond(req, String.valueOf(timestamp));
        String gErr = result.getErrorCodeName();
        if(gErr != null){
            pmr.setStatus("[nOK]\n" + gErr);
        }else{
            pmr.setStatus(err == null ? "[OK]" : "[nOK]\n" + err.getMessage());
        }
        pmr.setMessageId(err == null ? result.getMessageId() : null);
        return pmr;
    }
    
    private static Message getMessage(long timestamp, PushMessageRequest pmr){
        Message.Builder b = new Message.Builder();
        b.collapseKey(pmr.getMessage().getName());
        b.addData("params", pmr.getMessageParams());
        b.addData("timestamp", String.valueOf(timestamp));
        b.addData("context", pmr.getMessageContext());
        b.delayWhileIdle(true);
        return b.build();
    }
}
