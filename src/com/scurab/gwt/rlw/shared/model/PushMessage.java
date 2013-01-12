package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class PushMessage implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 9024107918887830744L;
    
    @SerializedName("Name")
    private String mName;
    
    @SerializedName ("HasParams")
    private boolean mHasParams;
    
    private transient String[] mPlatforms;
    
    @SerializedName("ParamExample")
    private String mParamExample;
    
    @SerializedName("OnlyForApp")
    private boolean mOnlyForApp;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean hasParams() {
        return mHasParams;
    }

    public void setHasParams(boolean hasParams) {
        mHasParams = hasParams;
    }

    public String[] getPlatforms() {
        return mPlatforms;
    }

    public void setPlatforms(String[] platforms) {
        mPlatforms = platforms;
    }
    
    public boolean isPlatformSupported(String platform){
        boolean result = mPlatforms == null;
        
        if(mPlatforms != null){
            for(String p : mPlatforms){
                if(p.equalsIgnoreCase(platform)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public String getParamExample() {
        return mParamExample;
    }

    public void setParamExample(String paramExample) {
        mParamExample = paramExample;
    }

    public boolean isOnlyForApp() {
        return mOnlyForApp;
    }

    public void setOnlyForApp(boolean onlyForApp) {
        mOnlyForApp = onlyForApp;
    }
}
