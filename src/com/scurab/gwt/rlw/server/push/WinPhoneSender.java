package com.scurab.gwt.rlw.server.push;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.push.android.Message;
import com.scurab.gwt.rlw.server.push.android.Result;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageResponse;

public class WinPhoneSender {

    private static final String TEXT_XML = "text/xml";

    private static final int TYPE_RAW = 0;
    private static final int TYPE_TOAST = 1;
    private static final int TYPE_TILE = 2;

    public static PushMessageResponse send(PushMessageRequest req) {
        final long timestamp = System.currentTimeMillis();
        PushMessageResponse response = new PushMessageResponse(req, String.valueOf(timestamp));
        try {
            int type = TYPE_RAW;
            if ("ToastNotification".equals(req.getMessage().getName())) {
                type = TYPE_TOAST;
            } else if ("TileNotification".equals(req.getMessage().getName())) {
                type = TYPE_TILE;
            }

            String msg = null;
            if (type == TYPE_RAW) {
                msg = getMessage(timestamp, req);
            } else {
                msg = req.getMessageParams();
            }

            HttpURLConnection conn = (HttpURLConnection) new URL(req.getPushToken()).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", TEXT_XML);
            conn.setFixedLengthStreamingMode(msg.length());
            conn.setRequestProperty("X-MessageID", UUID.randomUUID().toString());

            if (type == TYPE_TOAST) {
                fillForToast(conn);
            } else if (type == TYPE_TILE) {
                fillForTile(conn);
            } else {
                fillForRawNotifiation(conn);
            }

            OutputStream out = conn.getOutputStream();
            InputStream is = null;
            

            try {
                out.write(msg.getBytes());
                is = conn.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer);
                String value = writer.toString();
                
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(e.getMessage());
            } finally {
                if(conn != null){
                    int code = conn.getResponseCode();
                    String status = String.format("[%s] Notification:%s Subscription:%s Device:%s", code,
                            conn.getHeaderField("X-NotificationStatus"), conn.getHeaderField("X-SubscriptionStatus"),
                            conn.getHeaderField("X-DeviceConnectionStatus"));
                    response.setStatus(status);
                    System.err.println(status);
                }
                close(out);
                close(is);
            }

        } catch (Exception e) {
            response.setStatus(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    private static String getMessage(long timestamp, PushMessageRequest pmr) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("name", pmr.getMessage().getName());
        result.put("timestamp", String.valueOf(timestamp));
        if (pmr.getMessageParams() != null) {
            result.put("params", String.valueOf(pmr.getMessageParams()));
        }
        if (pmr.getMessageContext() != null) {
            result.put("context", String.valueOf(pmr.getMessageContext()));
        }
        return Application.GSON.toJson(result);
    }

    private static void fillForRawNotifiation(HttpURLConnection conn) {
        conn.setRequestProperty("X-NotificationClass", "3");
    }

    private static void fillForToast(HttpURLConnection conn) {
        conn.setRequestProperty("X-WindowsPhone-Target", "toast");
        conn.setRequestProperty("X-NotificationClass", "2");
    }

    private static void fillForTile(HttpURLConnection conn) {
        conn.setRequestProperty("X-WindowsPhone-Target", "token");
        conn.setRequestProperty("X-NotificationClass", "1");
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore error
                e.printStackTrace();
            }
        }
    }
}
