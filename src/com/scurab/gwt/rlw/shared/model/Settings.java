package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.gson.annotations.SerializedName;

@Entity(name = "Settings")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"AppName","DeviceID"},name="IX_AppNameDevice")})
public class Settings implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1680757323178371221L;

    @Id
    @GeneratedValue()
    @Column(name = "SettingsID", nullable = false)
    @SerializedName("SettingsID")
    private int mSettingsID;
    
    @Column(name = "AppName", nullable = false)
    @SerializedName("AppName")
    private String mAppName;
    
    @Column(name = "DeviceID", nullable = true)
    @SerializedName("DeviceID")
    private Integer mDeviceID;
    
    @Column(name = "JsonValue", nullable = false)
    @SerializedName("JsonValue")
    private String mJsonValue;

    public int getSettingsID() {
        return mSettingsID;
    }

    public void setSettingsID(int settingsID) {
        mSettingsID = settingsID;
    }

    public String getAppName() {
        return mAppName;
    }

    public void setAppName(String appName) {
        mAppName = appName;
    }

    public Integer getDeviceID() {
        return mDeviceID;
    }

    public void setDeviceID(Integer deviceID) {
        mDeviceID = deviceID;
    }

    public String getJsonValue() {
        return mJsonValue;
    }

    public void setJsonValue(String jsonValue) {
        mJsonValue = jsonValue;
    }
    
   
}
