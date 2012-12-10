package com.scurab.gwt.rlw.shared.model;

import com.google.gson.annotations.SerializedName;

public class Respond<T> {

    @SerializedName("Type")
    private String mType;
    
    @SerializedName("Message")
    private String mMessage;
    
    @SerializedName("HasError")
    private boolean hasError;

    @SerializedName("Context")
    private T mContext;
    
    @SerializedName("Count")
    private int mCount;

    /**
     * OK, count = 1, null Context
     */
    public Respond() {
        this("OK", 1);
    }

    /**
     * OK, count = 1, Context
     * @param context
     */
    public Respond(T context) {
        this();
        mContext = context;
    }

    /**
     * OK, count, null Context
     * @param count
     */
    public Respond(int count) {
        this("OK", count);
    }
    
    public Respond(String msg, int count) {
        mMessage = msg;
        mCount = count;
    }

    /**
     * Count = 1
     * @param msg
     * @param context
     */
    public Respond(String msg, T context) {
        this(msg, 1);
        mContext = context;
    }

    /**
     * Count = 0
     * @param t
     */
    public Respond(Throwable t) {
        mMessage = t.getMessage();
        mType = t.getClass().getName();
        hasError = true;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public boolean hasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public T getContext() {
        return mContext;
    }

    public void setContext(T context) {
        mContext = context;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
