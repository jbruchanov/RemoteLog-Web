package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Device implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5526599861310748107L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int mId;

    @Column(name = "Brand")
    private String mBrand;
    
    @Column(nullable = false, name = "Platform")
    private String mPlatform;
    
    @Column(name = "Version")
    private String mVersion;
    
    @Column(name = "Detail")
    private String mDetail;
    
    @Column(name = "Resolution")
    private String mResolution;
    
    @Column(name = "Owner")
    private String mOwner;
    
    @Column(name = "OSDescription")
    private String mOsDescription;

    @Column(name = "Description")
    private String mDescription;
    
    @Column(name = "PushID")
    private String mPushId;

    @Column(name = "DevUUID", nullable = false)
    private String mDevUUID;

    @Column(name = "Model")
    private String mModel;

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
}
