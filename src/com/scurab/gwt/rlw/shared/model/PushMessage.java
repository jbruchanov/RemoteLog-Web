package com.scurab.gwt.rlw.shared.model;

import java.io.Serializable;

public class PushMessage implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 9024107918887830744L;
    private String mName;
    private boolean mHasParams;

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
}
