package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.gson.annotations.SerializedName;
import com.google.gwt.i18n.client.LocalizableResource.Key;

@Entity(name = "Devices")
public class Device implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5526599861310748107L;

    @Id
    @GeneratedValue()
    @Column(name = "DeviceID", nullable = false)
    @SerializedName("DeviceID")
    private int mDeviceId;

    @Column(name = "DevUUID", nullable = false)
    @SerializedName("DevUUID")
    private String mDevUUID;

    @Column(name = "Brand")
    @SerializedName("Brand")
    private String mBrand;
    
    @Column(nullable = false, name = "Platform")
    @SerializedName("Platform")
    private String mPlatform;
    
    @Column(name = "Version")
    @SerializedName("Version")
    private String mVersion;
    
    @Column(name = "Detail")
    @SerializedName("Detail")
    private String mDetail;
    
    @Column(name = "Resolution")
    @SerializedName("Resolution")
    private String mResolution;
    
    @Column(name = "Owner")
    @SerializedName("Owner")
    private String mOwner;
    
    @Column(name = "OSDescription")
    @SerializedName("OSDescription")
    private String mOsDescription;

    @Column(name = "Description")
    @SerializedName("Description")
    private String mDescription;
    
    @Column(name = "PushID")
    @SerializedName("PushID")
    private String mPushId;

    @Column(name = "Model")
    @SerializedName("Model")
    private String mModel;

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String brand) {
        mBrand = brand;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String platform) {
        mPlatform = platform;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        mVersion = version;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public String getResolution() {
        return mResolution;
    }

    public void setResolution(String resolution) {
        mResolution = resolution;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPushId() {
        return mPushId;
    }

    public void setPushId(String pushId) {
        mPushId = pushId;
    }

    public String getDevUUID() {
        return mDevUUID;
    }

    public void setDevUUID(String devUUID) {
        mDevUUID = devUUID;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        mModel = model;
    }

    public int getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(int deviceId) {
        mDeviceId = deviceId;
    }
}
