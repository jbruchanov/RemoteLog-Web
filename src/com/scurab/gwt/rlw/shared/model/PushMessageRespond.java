package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

public class PushMessageRespond implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8524329985646553136L;

    @SerializedName("Request")
    private PushMessageRequest mRequest;
    
    @SerializedName("MessageID")
    private String mMessageId;
    
    @SerializedName("Status")
    private String mStatus;
    
    @SerializedName("Timestamp")
    private String mTimestamp;
    
    protected PushMessageRespond(){
    }
    
    public PushMessageRespond(PushMessageRequest req, String timestamp){
        mRequest = req;
        mTimestamp = timestamp;
    }

    public PushMessageRequest getRequest() {
        return mRequest;
    }

    public void setRequest(PushMessageRequest request) {
        mRequest = request;
    }

    public String getMessageId() {
        return mMessageId;
    }

    public void setMessageId(String messageId) {
        mMessageId = messageId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }
    
    public static PushMessageRespond fromJson(String json) {
        JSONObject jso = JSONParser.parseStrict(json).isObject();
        return fromJson(jso);
    }
    
    public static PushMessageRespond fromJson(JSONObject jso) {
        PushMessageRespond pmr = new PushMessageRespond();
        
        pmr.setRequest(PushMessageRequest.fromJson(jso.get("Request").isObject()));
        pmr.setMessageId(jso.get("MessageID").isString().stringValue());
        pmr.setStatus(jso.get("Status").isString().stringValue());
        pmr.setTimestamp(jso.get("Timestamp").isString().stringValue());
        return pmr;
    }
}
