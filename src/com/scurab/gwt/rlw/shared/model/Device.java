package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.scurab.gwt.rlw.server.Application;

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
    private int mDeviceID;

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
    private String mOSDescription;

    @Column(name = "Description")
    @SerializedName("Description")
    private String mDescription;

    @Column(name = "PushID")
    @SerializedName("PushID")
    private String mPushID;

    @Column(name = "Model")
    @SerializedName("Model")
    private String mModel;

    public int getDeviceID() {
        return mDeviceID;
    }

    public void setDeviceID(int deviceID) {
        mDeviceID = deviceID;
    }

    public String getDevUUID() {
        return mDevUUID;
    }

    public void setDevUUID(String devUUID) {
        mDevUUID = devUUID;
    }

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

    public String getOSDescription() {
        return mOSDescription;
    }

    public void setOSDescription(String oSDescription) {
        mOSDescription = oSDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPushID() {
        return mPushID;
    }

    public void setPushID(String pushID) {
        mPushID = pushID;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        mModel = model;
    }

    public void updateValues(Device other){
        mBrand = other.mBrand;
        mDescription = other.mDescription;
        mDetail = other.mDetail;
//        mDeviceID = other.mDeviceID;
//        mDevUUID = other.mDevUUID;
        mModel = other.mModel;
        mOSDescription = other.mOSDescription;
        mOwner = other.mOwner;
        mPlatform = other.mPlatform;
        mPushID = other.mPushID;
        mResolution = other.mResolution;
        mVersion = other.mVersion;
    }
}
