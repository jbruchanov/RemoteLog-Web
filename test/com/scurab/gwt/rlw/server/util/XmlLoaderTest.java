package com.scurab.gwt.rlw.server.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.scurab.gwt.rlw.shared.model.PushMessage;

public class XmlLoaderTest {

    @Test
    public void testLoadPush() {
       PushMessage[] v = XmlLoader.loadMessages();
       assertNotNull(v);
       assertTrue(v.length > 0);
    }
    @Test
    public void testLoadServerPushTokens() {
       DoubleHashMap<String, String, String> v = XmlLoader.loadServerPushTokens();
       assertNotNull(v);
       assertTrue(v.size() > 0);
    }

}
