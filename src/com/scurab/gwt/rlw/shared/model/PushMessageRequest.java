package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

public class PushMessageRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5844559432126831763L;

    @SerializedName("DeviceID")
    private int mDeviceID;
    
    @SerializedName("DevicePlatform")
    private String mDevicePlatform;

    @SerializedName("PushToken")
    private String mPushToken;

    @SerializedName("Message")
    private PushMessage mMessage;

    @SerializedName("MessageParams")
    private String mMessageParams;
    
    @SerializedName("MessageContext")
    private String mMessageContext;

    @SerializedName("WaitForRespond")
    private boolean mWaitForRespond;

    public int getDeviceID() {
        return mDeviceID;
    }

    public void setDeviceID(int deviceId) {
        mDeviceID = deviceId;
    }

    public PushMessage getMessage() {
        return mMessage;
    }

    public void setMessage(PushMessage message) {
        mMessage = message;
    }

    public String getMessageParams() {
        return mMessageParams;
    }

    public void setMessageParams(String messageParams) {
        mMessageParams = messageParams;
    }

    public boolean isWaitForRespond() {
        return mWaitForRespond;
    }

    public void setWaitForRespond(boolean waitForRespond) {
        mWaitForRespond = waitForRespond;
    }

    public String getPushToken() {
        return mPushToken;
    }

    public void setPushToken(String pushToken) {
        mPushToken = pushToken;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("DeviceID", new JSONNumber(mDeviceID));
        obj.put("DevicePlatform", new JSONString(getDevicePlatform()));
        obj.put("PushToken", new JSONString(mPushToken));
        obj.put("Message", mMessage.toJson());
        if(mMessageParams == null){
            mMessageParams = "";
        }
        obj.put("MessageParams", new JSONString(mMessageParams));
        
        if(mMessageContext == null){
            mMessageContext = "";
        }
        obj.put("MessageContext", new JSONString(mMessageContext));
        obj.put("WaitForRespond", JSONBoolean.getInstance(mWaitForRespond));
        return obj;
    }

    public static PushMessageRequest fromJson(String json) {
        JSONObject jso = JSONParser.parseStrict(json).isObject();
        return fromJson(jso);
    }
    
    public static PushMessageRequest fromJson(JSONObject jso) {
        PushMessageRequest pmr = new PushMessageRequest();
        
        pmr.mDeviceID = ((int)jso.get("DeviceID").isNumber().doubleValue());
        pmr.setDevicePlatform((jso.get("DevicePlatform").isString().stringValue()));
        pmr.mMessage = (PushMessage.fromJson(jso.get("Message").isObject()));
        pmr.mMessageParams = (jso.get("MessageParams").isString().stringValue());
        pmr.mPushToken = (jso.get("PushToken").isString().stringValue());
        pmr.mWaitForRespond = (jso.get("WaitForRespond").isBoolean().booleanValue());
        
        return pmr;
    }

    public String getDevicePlatform() {
        return mDevicePlatform;
    }

    public void setDevicePlatform(String devicePlatform) {
        mDevicePlatform = devicePlatform;
    }

    public String getMessageContext() {
        return mMessageContext;
    }

    public void setMessageContext(String messageContext) {
        mMessageContext = messageContext;
    }
}
