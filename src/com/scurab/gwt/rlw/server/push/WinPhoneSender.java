package com.scurab.gwt.rlw.server.push;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.scurab.gwt.rlw.server.push.android.Result;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageRespond;

public class WinPhoneSender {

    private static final String TEXT_XML = "text/xml";

    public static PushMessageRespond send(PushMessageRequest req) {
        final long timestamp = System.currentTimeMillis();
        Result result = null;
        Exception err = null;

        try {
            /*
             * HttpWebRequest request = (HttpWebRequest)WebRequest.Create(deviceUri); request.Method =
             * WebRequestMethods.Http.Post; request.ContentType = "text/xml"; request.ContentLength = msg.Length;
             * request.Headers["X-MessageID"] = Guid.NewGuid().ToString(); request.Headers["X-WindowsPhone-Target"] =
             * "toast"; request.Headers["X-NotificationClass"] = "2";
             * 
             * // post the payload Stream requestStream = request.GetRequestStream(); requestStream.Write(msgBytes, 0,
             * msgBytes.Length); requestStream.Close();
             */

            String msg = req.getMessageContext();

            HttpURLConnection conn = (HttpURLConnection) new URL(req.getPushToken()).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", TEXT_XML);
            conn.setFixedLengthStreamingMode(msg.length());
            conn.setRequestProperty("X-MessageID", UUID.randomUUID().toString());

            if ("ToastNotification".equals(req.getMessage().getName())) {
                fillForToast(conn);
            } else {
                throw new IllegalStateException("Not implemented");
            }

            OutputStream out = conn.getOutputStream();
            InputStream is = null;
            String theString = null;
            try {
                out.write(msg.getBytes());
                is = conn.getInputStream();
                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer);
                theString = writer.toString();
            }
            catch(Exception e){
                e.printStackTrace();
            } finally {
                close(out);
                close(is);
            }

        } catch (Exception e) {
            err = e;
            e.printStackTrace();
        }
return null;
        /*
        PushMessageRespond pmr = new PushMessageRespond(req, String.valueOf(timestamp));
        String gErr = result.getErrorCodeName();
        if (gErr != null) {
            pmr.setStatus("[nOK]\n" + gErr);
        } else {
            pmr.setStatus(err == null ? "[OK]" : "[nOK]\n" + err.getMessage());
        }
        pmr.setMessageId(err == null ? result.getMessageId() : null);
        return pmr;
        */
    }

    private static void fillForToast(HttpURLConnection conn) {
        conn.setRequestProperty("X-WindowsPhone-Target", "toast");
        conn.setRequestProperty("X-NotificationClass", "2");
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
