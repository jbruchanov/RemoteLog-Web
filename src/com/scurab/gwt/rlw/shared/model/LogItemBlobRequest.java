package com.scurab.gwt.rlw.shared.model;

import com.google.gson.annotations.SerializedName;

public class LogItemBlobRequest {

    @SerializedName("LogItemID")
    private int mLogItemID;
    
    @SerializedName("MimeType")
    private String mMimeType;
    
    @SerializedName("DataLength")
    private int mDataLength;
}