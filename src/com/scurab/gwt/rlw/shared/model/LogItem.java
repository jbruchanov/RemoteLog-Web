package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.gson.annotations.SerializedName;
import com.scurab.gwt.rlw.server.annotation.DefaultOrderString;

@Entity(name = "LogItems")
@DefaultOrderString(value = "ID DESC")
public class LogItem implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7649642015381756913L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    @SerializedName("ID")
    private int mID;

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
    
    @Column(name = "Source")
    @SerializedName("Source")
    private String mSource;

    @Column(name = "Message")
    @SerializedName("Message")
    private String mMessage;

    @Column(name = "BlobMime")
    @SerializedName("BlobMime")
    private String mBlobMime;

    @Column(name = "DeviceID")
    @SerializedName("DeviceID")
    private int mDeviceID;

    public int getID() {
        return mID;
    }

    public void setID(int iD) {
        mID = iD;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getBlobMime() {
        return mBlobMime;
    }

    public void setBlobMime(String blobMime) {
        mBlobMime = blobMime;
    }

    public int getDeviceID() {
        return mDeviceID;
    }

    public void setDeviceID(int deviceID) {
        mDeviceID = deviceID;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

}