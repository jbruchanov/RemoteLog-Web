package com.google.android.gcm.server;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map.Entry;
import java.util.Random;

import com.google.android.gcm.server.InvalidRequestException;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Result.Builder;
import com.scurab.gwt.rlw.server.push.PushSender;

public class AndroidSender extends PushSender {
    protected static final String UTF8 = "UTF-8";

    /**
     * Initial delay before first retry, without jitter.
     */
    protected static final int BACKOFF_INITIAL_DELAY = 1000;
    /**
     * Maximum delay before a retry.
     */
    protected static final int MAX_BACKOFF_DELAY = 1024000;

    protected final Random random = new Random();
//    protected static final Logger logger = Logger.getLogger(Sender.class.getName());

    private final String key;

    /**
     * Default constructor.
     * 
     * @param key
     *            API key obtained through the Google API Console.
     */

    public AndroidSender() {
        this.key = "AIzaSyC8TBGwFPjeVul9FPs5VlmCkht8zfCVFyM";
    }

    public AndroidSender(String key) {
        this.key = key;
    }

    protected static StringBuilder newBody(String name, String value) {
        return new StringBuilder(nonNull(name)).append('=').append(nonNull(value));
    }

    static <T> T nonNull(T argument) {
        if (argument == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        return argument;
    }

    protected static void addParameter(StringBuilder body, String name, String value) {
        nonNull(body).append('&').append(nonNull(name)).append('=').append(nonNull(value));
    }

    /**
     * Sends a message without retrying in case of service unavailability. See
     * {@link #send(Message, String, int)} for more info.
     * 
     * @return result of the post, or {@literal null} if the GCM service was unavailable or any
     *         network exception caused the request to fail.
     * 
     * @throws InvalidRequestException
     *             if GCM didn't returned a 200 or 5xx status.
     * @throws IllegalArgumentException
     *             if registrationId is {@literal null}.
     */
    public Result sendNoRetry(Message message, String registrationId) throws IOException {
        StringBuilder body = newBody(Constants.PARAM_REGISTRATION_ID, registrationId);
        Boolean delayWhileIdle = message.isDelayWhileIdle();
        if (delayWhileIdle != null) {
            addParameter(body, Constants.PARAM_DELAY_WHILE_IDLE, delayWhileIdle ? "1" : "0");
        }
        String collapseKey = message.getCollapseKey();
        if (collapseKey != null) {
            addParameter(body, Constants.PARAM_COLLAPSE_KEY, collapseKey);
        }
        Integer timeToLive = message.getTimeToLive();
        if (timeToLive != null) {
            addParameter(body, Constants.PARAM_TIME_TO_LIVE, Integer.toString(timeToLive));
        }
        for (Entry<String, String> entry : message.getData().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key == null || value == null) {
//                logger.warning("Ignoring payload entry thas has null: " + entry);
            } else {
                key = Constants.PARAM_PAYLOAD_PREFIX + key;
                addParameter(body, key, URLEncoder.encode(value, UTF8));
            }
        }
        String requestBody = body.toString();
//        logger.finest("Request body: " + requestBody);
        HttpURLConnection conn;
        int status;
        try {
            conn = post(Constants.GCM_SEND_ENDPOINT, requestBody);
            status = conn.getResponseCode();
        } catch (IOException e) {
//            logger.log(Level.FINE, "IOException posting to GCM", e);
            return null;
        }
        if (status / 100 == 5) {
//            logger.fine("GCM service is unavailable (status " + status + ")");
            return null;
        }
        String responseBody;
        if (status != 200) {
            try {
                responseBody = getAndClose(conn.getErrorStream());
//                logger.finest("Plain post error response: " + responseBody);
            } catch (IOException e) {
                // ignore the exception since it will thrown an
                // InvalidRequestException
                // anyways
                responseBody = "N/A";
//                logger.log(Level.FINE, "Exception reading response: ", e);
            }
            throw new InvalidRequestException(status, responseBody);
        } else {
            try {
                responseBody = getAndClose(conn.getInputStream());
            } catch (IOException e) {
//                logger.log(Level.WARNING, "Exception reading response: ", e);
                // return null so it can retry
                return null;
            }
        }
        String[] lines = responseBody.split("\n");
        if (lines.length == 0 || lines[0].equals("")) {
            throw new IOException("Received empty response from GCM service.");
        }
        String firstLine = lines[0];
        String[] responseParts = split(firstLine);
        String token = responseParts[0];
        String value = responseParts[1];
        if (token.equals(Constants.TOKEN_MESSAGE_ID)) {
            Builder builder = new Result.Builder().messageId(value);
            // check for canonical registration id
            if (lines.length > 1) {
                String secondLine = lines[1];
                responseParts = split(secondLine);
                token = responseParts[0];
                value = responseParts[1];
                if (token.equals(Constants.TOKEN_CANONICAL_REG_ID)) {
                    builder.canonicalRegistrationId(value);
                } else {
//                    logger.warning("Invalid response from GCM: " + responseBody);
                }
            }
            Result result = builder.build();
//            if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Message created succesfully (" + result + ")");
//            }
            return result;
        } else if (token.equals(Constants.TOKEN_ERROR)) {
            return new Result.Builder().errorCode(value).build();
        } else {
            throw new IOException("Invalid response from GCM: " + responseBody);
        }
    }

    protected HttpURLConnection post(String url, String body) throws IOException {
        return post(url, "application/x-www-form-urlencoded;charset=UTF-8", body);
    }

    protected HttpURLConnection post(String url, String contentType, String body) throws IOException {
        if (url == null || body == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        if (!url.startsWith("https://")) {
//            logger.warning("URL does not use https: " + url);
        }
//        logger.fine("Sending POST to " + url);
//        logger.finest("POST body: " + body);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = getConnection(url);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setFixedLengthStreamingMode(bytes.length);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Authorization", "key=" + key);
        OutputStream out = conn.getOutputStream();
        try {
            out.write(bytes);
        } finally {
            close(out);
        }
        return conn;
    }

    protected HttpURLConnection getConnection(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        return conn;
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore error
//                logger.log(Level.FINEST, "IOException closing stream", e);
            }
        }
    }

    private String[] split(String line) throws IOException {
        String[] split = line.split("=", 2);
        if (split.length != 2) {
            throw new IOException("Received invalid response line from GCM: " + line);
        }
        return split;
    }

    private static String getAndClose(InputStream stream) throws IOException {
        try {
            return getString(stream);
        } finally {
            if (stream != null) {
                close(stream);
            }
        }
    }

    protected static String getString(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder content = new StringBuilder();
        String newLine;
        do {
            newLine = reader.readLine();
            if (newLine != null) {
                content.append(newLine).append('\n');
            }
        } while (newLine != null);
        if (content.length() > 0) {
            // strip last newline
            content.setLength(content.length() - 1);
        }
        return content.toString();
    }
}
