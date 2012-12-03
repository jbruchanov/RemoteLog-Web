package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.gson.annotations.SerializedName;

@Entity(name = "LogItems")
public class LogItem implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7649642015381756913L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    @SerializedName("ID")
    private int mId;
    
    @Column(name = "Application", nullable = false)
    @SerializedName("Application")
    private String mApplication;

    @Column(name = "AppVersion")
    @SerializedName("AppVersion")
    private String mAppVersion;

    @Column(name = "AppBuild")
    @SerializedName("AppBuild")
    private String mAppBuild;

    @Column(name = "Date", nullable = false)
    @SerializedName("Date")
    private Date mDate;
    
    @Column(name = "Category")
    @SerializedName("Category")
    private String mCategory;
    
    @Column(name = "Message")
    @SerializedName("Message")
    private String mMesage;
    
    @Column(name = "DataType")
    @SerializedName("DataType")
    private String mBlobMime;
    
    @Column(name = "Data")
    @SerializedName("Data")
    private byte[] mBlob;

    @Column(name = "Platform")
    @SerializedName("Platform")
    private String mPlatform;

    @Column(name = "DeviceID")
    @SerializedName("DeviceID")
    private int mDeviceId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public int getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(int deviceId) {
        mDeviceId = deviceId;
    }
}
