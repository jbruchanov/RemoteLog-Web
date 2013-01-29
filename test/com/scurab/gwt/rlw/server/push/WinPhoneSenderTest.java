package com.scurab.gwt.rlw.server.push;

import static org.junit.Assert.*;

import org.junit.Test;

import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;

import com.scurab.gwt.rlw.shared.model.PushMessageRequest;

public class WinPhoneSenderTest {

    
    private static final String PUSH_EMU = "http://db3.notify.live.net/throttledthirdparty/01.00/AAFbc87n_MycRrzZi8QkVnWXAgAAAAADAQAAAAQUZm52OjIzOEQ2NDJDRkI5MEVFMEQ";
    private static final String PUSH_DEV = "http://db3.notify.live.net/throttledthirdparty/01.00/AAE6YV-nPPXBR6T_7QPLLT8pAgAAAAADDAAAAAQUZm52OkJCMjg1QTg1QkZDMkUxREQ";
    @Test
    public void testSendToast() {
        
        PushMessage pm = new PushMessage();
        pm.setName("ToastNotification");
        
        PushMessageRequest pmr = new PushMessageRequest();
        pmr.setDeviceID(2);
        pmr.setDevicePlatform("WindowsPhone");        
        pmr.setMessage(pm);
        pmr.setMessageParams(toast);
        
        pmr.setPushToken(PUSH_EMU);
        
        WinPhoneSender.send(pmr);
    }
    
    @Test
    public void testSendTile() {
        
        PushMessage pm = new PushMessage();
        pm.setName("TileNotification");
        
        PushMessageRequest pmr = new PushMessageRequest();
        pmr.setDeviceID(2);
        pmr.setDevicePlatform("WindowsPhone");        
        pmr.setMessage(pm);
        pmr.setMessageParams(tile);
        
        pmr.setPushToken(PUSH_EMU);
        
        WinPhoneSender.send(pmr);
    }
    
    @Test
    public void testSendRaw() {
        
        PushMessage pm = new PushMessage();
        pm.setName("Raw");
        
        PushMessageRequest pmr = new PushMessageRequest();
        pmr.setDeviceID(2);
        pmr.setDevicePlatform("WindowsPhone");        
        pmr.setMessage(pm);
        pmr.setMessageParams("abcde");
        
        pmr.setPushToken(PUSH_EMU);
        
        WinPhoneSender.send(pmr);
    }
    
    @Test
    public void testSendMessage()
    {
        PushMessage pm = new PushMessage();
        pm.setName("ReloadSettings");
        
        PushMessageRequest pmr = new PushMessageRequest();
        pmr.setDeviceID(2);
        pmr.setDevicePlatform("WindowsPhone");        
        pmr.setMessage(pm);
        pmr.setPushToken(PUSH_EMU);
        
        WinPhoneSender.send(pmr);
    }

    
    private static final String toast = "<?xml version=\"1.0\" encoding=\"utf-8\"?><wp:Notification xmlns:wp=\"WPNotification\"><wp:Toast><wp:Text1>string</wp:Text1><wp:Text2>string</wp:Text2></wp:Toast></wp:Notification>";
    private static final String tile = "<?xml version=\"1.0\" encoding=\"utf-8\"?><wp:Notification xmlns:wp=\"WPNotification\"><wp:Tile><wp:Count>12</wp:Count><wp:Title>Test</wp:Title></wp:Tile></wp:Notification>";
}
