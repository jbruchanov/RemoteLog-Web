package com.scurab.gwt.rlw.server.push;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.push.android.Message;
import com.scurab.gwt.rlw.server.push.android.Result;
import com.scurab.gwt.rlw.server.push.android.Sender;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageResponse;

public class AndroidSender{

    private static final String DEFAULT_APP_NAME = "RemoteLog";
    private static final String PLATFORM = "Android";

    public static PushMessageResponse send(PushMessageRequest req) {
        final long timestamp = System.currentTimeMillis();
        Result result = null;
        Exception err = null;
        
        try
        {            
            //get key for platform and app
            String key = Application.SERVER_PUSH_KEYS.get(PLATFORM, req.getAppName());
            if(key == null || key.length() == 0){
                key = Application.SERVER_PUSH_KEYS.get(PLATFORM, DEFAULT_APP_NAME);
            }
            if(key == null || key.length() == 0){
                throw new IllegalStateException(
                        String.format("Unable to find default push key for server nor default one Platform:%s App:%s", PLATFORM,
                        req.getAppName()));
            }
            //send
            Sender mSender = new Sender(key);
            Message m = getMessage(timestamp, req);
            String token = req.getPushToken();
            result = mSender.sendNoRetry(m, token);
        
        }catch(Exception e){
            err = e;
            e.printStackTrace();
        }
        
        PushMessageResponse pmr = new PushMessageResponse(req, String.valueOf(timestamp));
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
