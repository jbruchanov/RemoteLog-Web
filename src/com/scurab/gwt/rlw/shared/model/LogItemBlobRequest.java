package com.scurab.gwt.rlw.shared.model;

import com.google.gson.annotations.SerializedName;

public class LogItemBlobRequest {

    @SerializedName("LogItemID")
    private int mLogItemID;

    @SerializedName("MimeType")
    private String mMimeType;

    @SerializedName("DataLength")
    private int mDataLength;

    @SerializedName("FileName")
    private String mFileName;

    public LogItemBlobRequest() {
    }

    public LogItemBlobRequest(int logItemId, String mime, int length, String fileName) {
        mLogItemID = logItemId;
        mMimeType = mime;
        mDataLength = length;
        mFileName = fileName;
    }

    public int getLogItemID() {
        return mLogItemID;
    }

    public void setLogItemID(int logItemID) {
        mLogItemID = logItemID;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public void setMimeType(String mimeType) {
        mMimeType = mimeType;
    }

    public int getDataLength() {
        return mDataLength;
    }

    public void setDataLength(int dataLength) {
        mDataLength = dataLength;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        mFileName = fileName;
    }
}