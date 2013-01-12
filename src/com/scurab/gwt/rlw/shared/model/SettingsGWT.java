package com.scurab.gwt.rlw.shared.model;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * Simple workaroud for not using 
 * gwt-user.jar on "server" side, becuase<br/>
 * <b>See Servlet Spec 2.3, section 9.7.2.</b>
 *
 * <b>This class is for usage only on client side!</b>
 * @author Joe Scurab 
 */
public class SettingsGWT extends Settings {

    /**
     * 
     */
    private static final long serialVersionUID = 5209545669501769394L;

    public SettingsGWT() {
    }
    
    public SettingsGWT(Settings s){
        setAppName(s.getAppName());
        setDeviceID(s.getDeviceID());
        setJsonValue(s.getJsonValue());
        setSettingsID(s.getSettingsID());
    }
    
    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        int mSettingsID = getSettingsID();
        obj.put("SettingsID", new JSONNumber(mSettingsID));
        
        String mAppName = getAppName();
        if(mAppName != null){
            obj.put("AppName", new JSONString(mAppName));
        }
        
        Integer mDeviceID = getDeviceID();
        if(mDeviceID != null){
            obj.put("DeviceID", new JSONNumber(mDeviceID));
        }
                
        obj.put("JsonValue", new JSONString(getJsonValue()));
        return obj;
    }
}
