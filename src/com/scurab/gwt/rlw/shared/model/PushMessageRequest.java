package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.google.gwt.user.client.rpc.IsSerializable;

public class PushMessageRequest implements Serializable, IsSerializable {
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
    
    @SerializedName("AppName")
    private String mAppName;

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

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }
}
