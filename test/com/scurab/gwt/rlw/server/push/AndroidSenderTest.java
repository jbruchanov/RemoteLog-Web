package com.scurab.gwt.rlw.server.push;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.scurab.gwt.rlw.ApplicationTest;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageRespond;

public class AndroidSenderTest extends ApplicationTest {

    @Test
    public void testSend() {
        PushMessage pm = new PushMessage();
        pm.setName("message");
        
        PushMessageRequest pmr = new PushMessageRequest();
        pmr.setPushToken("APA91bH5gMrR4QaIt3_ejHnbkwpNBhJfvexXaOW4IMeQGbmo1i-Mutrkfb_BzbK0XIRuxezh5G3RbyVM766doIrKy9NY6_joJ6iMphS_eYtb-h1Syhfq5KRjDoOy2RBrlI26z7jufq7Dq_iDGLXCWy7C_ay-OgCOmg");
                //        APA91bH5gMrR4QaIt3_ejHnbkwpNBhJfvexXaOW4IMeQGbmo1i-Mutrkfb_BzbK0XIRuxezh5G3RbyVM766doIrKy9NY6_joJ6iMphS_eYtb-h1Syhfq5KRjDoOy2RBrlI26z7jufq7Dq_iDGLXCWy7C_ay-OgCOmg
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
