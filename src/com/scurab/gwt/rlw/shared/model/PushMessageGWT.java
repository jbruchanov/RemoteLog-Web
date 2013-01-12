package com.scurab.gwt.rlw.shared.model;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

public class PushMessageGWT extends PushMessage{
    
    public PushMessageGWT() {
        // TODO Auto-generated constructor stub
    }
    
    
    public PushMessageGWT(PushMessage pm) {
        setHasParams(pm.hasParams());
        setName(pm.getName());
        setOnlyForApp(pm.isOnlyForApp());
        setParamExample(pm.getParamExample());
        setPlatforms(pm.getPlatforms());
    }
    
    public JSONObject toJson(){
        return toJson(this);
    }
    
    public static PushMessage fromJson(String json){
        JSONObject jso = JSONParser.parseStrict(json).isObject();
        return fromJson(jso);
    }
    
    public static PushMessage fromJson(JSONObject jso){
        PushMessageGWT pm = new PushMessageGWT();
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
}
