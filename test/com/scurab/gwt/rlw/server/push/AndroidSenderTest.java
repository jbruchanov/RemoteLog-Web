package com.scurab.gwt.rlw.server.push;

import static org.junit.Assert.*;

import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageRespond;

public class AndroidSenderTest extends ApplicationTest {

    @Test
    public void testSendZumpaReader() {
        PushMessage pm = new PushMessage();
        pm.setName("message");
        
        PushMessageRequest pmr = new PushMessageRequest();
        pmr.setPushToken("APA91bHdakg6HvdPvQerb33AJb-oCcKCqmdmGLyG7krS9OAhec1VgTX5o2BxJwNoDr2kquEXdK5P5TelFiFuQAYXql3eheFPMdL3wktDa7tJYFU90EIVmRG_Yr7xKfDRcqFNlrEhNIOIakw1OUqPOMqBPB4McQsyzg");
        
        pmr.setDeviceID(0);
        pmr.setDevicePlatform("android");
        pmr.setMessage(pm);
        pmr.setMessageParams("HelloThere");
        
        PushMessageRespond respond = AndroidSender.send(pmr);
        
        assertNotNull(respond);
        assertNotNull(respond.getMessageId());
        assertTrue(respond.getRequest() == pmr);
    }
}
