package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;
import java.util.Date;

public class LogItem implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7649642015381756913L;

    private int mId;
    
    private String mApplication;

    private String mAppVersion;

    private String mAppBuild;

    private String mDeviceId;
    
    private Date mDate;
    
    private String mPlatform;
    
    private String mCategory;
    
    private String mMesage;
    
    private String mBlobMime;
    
    private byte[] mBlob;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String platform) {
        mPlatform = platform;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getMesage() {
        return mMesage;
    }

    public void setMesage(String mesage) {
        mMesage = mesage;
    }

    public String getBlobMime() {
        return mBlobMime;
    }

    public void setBlobMime(String blobMime) {
        mBlobMime = blobMime;
    }

    public byte[] getBlob() {
        return mBlob;
    }

    public void setBlob(byte[] blob) {
        mBlob = blob;
    }

    public String getApplication() {
        return mApplication;
    }

    public void setApplication(String application) {
        mApplication = application;
    }
}
