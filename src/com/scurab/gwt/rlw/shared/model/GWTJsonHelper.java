package com.scurab.gwt.rlw.shared.model;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

public class GWTJsonHelper {

    public static PushMessage fromJsonPushMessage(JSONObject jso){
        PushMessage pm = new PushMessage();
        pm.setName(jso.get("Name").isString().stringValue());
        pm.setHasParams(jso.get("HasParams").isBoolean().booleanValue());
        return pm;
    }
    public static JSONObject toJson(PushMessage message) {
        JSONObject obj = new JSONObject();
        obj.put("Name", new JSONString(message.getName()));
        obj.put("HasParams", JSONBoolean.getInstance(message.hasParams()));
        return obj;
    }
    
    public static JSONObject toJson(Settings s){
        JSONObject obj = new JSONObject();
        int mSettingsID = s.getSettingsID();
        obj.put("SettingsID", new JSONNumber(mSettingsID));
        
        String mAppName = s.getAppName();
        if(mAppName != null){
            obj.put("AppName", new JSONString(mAppName));
        }
        
        Integer mDeviceID = s.getDeviceID();
        if(mDeviceID != null){
            obj.put("DeviceID", new JSONNumber(mDeviceID));
        }
                
        obj.put("JsonValue", new JSONString(s.getJsonValue()));
        return obj;
    }
    
    public static JSONObject toJson(PushMessageRequest pmr) {
        JSONObject obj = new JSONObject();
        obj.put("DeviceID", new JSONNumber(pmr.getDeviceID()));
        obj.put("DevicePlatform", new JSONString(pmr.getDevicePlatform()));
        obj.put("PushToken", new JSONString(pmr.getPushToken()));
        obj.put("Message", toJson(pmr.getMessage()));
        if(pmr.getMessageParams() == null){
            pmr.setMessageParams("");
        }
        obj.put("MessageParams", new JSONString(pmr.getMessageParams()));
        
        if(pmr.getMessageContext() == null){
            pmr.setMessageContext("");
        }
        obj.put("MessageContext", new JSONString(pmr.getMessageContext()));
        obj.put("WaitForRespond", JSONBoolean.getInstance(pmr.isWaitForRespond()));
        return obj;
    }

    public static PushMessageRequest fromJsonPushMessageRequest(String json) {
        JSONObject jso = JSONParser.parseStrict(json).isObject();
        return fromJsonPushMessageRequest(jso);
    }
    
    public static PushMessageRequest fromJsonPushMessageRequest(JSONObject jso) {
        PushMessageRequest pmr = new PushMessageRequest();
        
        pmr.setDeviceID((int)jso.get("DeviceID").isNumber().doubleValue());
        pmr.setDevicePlatform((jso.get("DevicePlatform").isString().stringValue()));
        pmr.setMessage(fromJsonPushMessage(jso.get("Message").isObject()));
        pmr.setMessageParams(jso.get("MessageParams").isString().stringValue());
        pmr.setPushToken(jso.get("PushToken").isString().stringValue());
        pmr.setWaitForRespond(jso.get("WaitForRespond").isBoolean().booleanValue());
        
        return pmr;
    }
    
    public static PushMessageResponse fromJsonPushMessageRespond(String json) {
        JSONObject jso = JSONParser.parseStrict(json).isObject();
        return fromJsonPushMessageRespond(jso);
    }
    
    public static PushMessageResponse fromJsonPushMessageRespond(JSONObject jso) {
        PushMessageResponse pmr = new PushMessageResponse();
        
        pmr.setRequest(fromJsonPushMessageRequest(jso.get("Request").isObject()));
        pmr.setMessageId(jso.get("MessageID").isString().stringValue());
        pmr.setStatus(jso.get("Status").isString().stringValue());
        pmr.setTimestamp(jso.get("Timestamp").isString().stringValue());
        return pmr;
    }
}
