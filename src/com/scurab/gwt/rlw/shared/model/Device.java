package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

public class Device implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5526599861310748107L;

    private int mId;
    
    private String mBrand;
    
    private String mPlatform;
    
    private String mVersion;
    
    private String mDetail;
    
    private String mResolution;
    
    private String mOwner;
    
    private String mDescription;
    
    private String mPushId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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
}
