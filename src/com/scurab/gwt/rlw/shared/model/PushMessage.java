package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

public class PushMessage implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 9024107918887830744L;
    private String mName;
    private boolean mHasParams;
    private String[] mPlatforms;

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
        boolean result = false;
        
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
}
