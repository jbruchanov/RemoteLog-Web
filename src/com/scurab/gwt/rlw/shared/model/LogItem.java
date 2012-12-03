package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "LogItems")
public class LogItem implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7649642015381756913L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int mId;
    
    @Column(name = "Application", nullable = false)
    private String mApplication;

    @Column(name = "AppVersion")
    private String mAppVersion;

    @Column(name = "AppBuild")
    private String mAppBuild;

    private String mDeviceId;
    
    @Column(name = "Date", nullable = false)
    private Date mDate;
    
    @Column(name = "Platform", nullable = false)
    private String mPlatform;
    
    @Column(name = "Category")
    private String mCategory;
    
    @Column(name = "Message")
    private String mMesage;
    
    @Column(name = "DataType")
    private String mBlobMime;
    
    @Column(name = "Data")
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

    public String getAppVersion() {
        return mAppVersion;
    }

    public void setAppVersion(String appVersion) {
        mAppVersion = appVersion;
    }

    public String getAppBuild() {
        return mAppBuild;
    }

    public void setAppBuild(String appBuild) {
        mAppBuild = appBuild;
    }
}
