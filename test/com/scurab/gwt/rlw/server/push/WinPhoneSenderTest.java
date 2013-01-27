package com.scurab.gwt.rlw.server.push;

import static org.junit.Assert.*;

import org.junit.Test;

import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;

import com.scurab.gwt.rlw.shared.model.PushMessageRequest;

public class WinPhoneSenderTest {

    @Test
    public void testSend() {
        
        PushMessage pm = new PushMessage();
        pm.setName("ToastNotification");
        
        PushMessageRequest pmr = new PushMessageRequest();
        pmr.setDeviceID(2);
        pmr.setDevicePlatform("WindowsPhone");        
        pmr.setMessage(pm);
        pmr.setMessageContext(toast);
        
        pmr.setPushToken("http://db3.notify.live.net/throttledthirdparty/01.00/AAE6YV-nPPXBR6T_7QPLLT8pAgAAAAADDAAAAAQUZm52OkJCMjg1QTg1QkZDMkUxREQ");
        
        WinPhoneSender.send(pmr);
    }

    
    private static final String toast = "<?xml version=\"1.0\" encoding=\"utf-8\"?><wp:Notification xmlns:wp=\"WPNotification\"><wp:Toast><wp:Text1>string</wp:Text1><wp:Text2>string</wp:Text2></wp:Toast></wp:Notification>";
}
