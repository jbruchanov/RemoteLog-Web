package com.scurab.gwt.rlw.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class DataLoadingEvent extends GwtEvent<DataLoadingEventHandler> {
    public static Type<DataLoadingEventHandler> TYPE = new Type<DataLoadingEventHandler>();
    public final static int START_LOADING = 1;
    public final static int STOP_LOADING = 2;
    private int mType = 0;
    private String mMessage;

    public DataLoadingEvent(int type) {
        mType = type;
    }

    public DataLoadingEvent(int type, String message) {
        mType = type;
        mMessage = message;
    }

    public int getType() {
        return mType;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getMessageIfNullEmptyString() {
        if (mMessage == null) {
            return "";
        }
        return mMessage;
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<DataLoadingEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(DataLoadingEventHandler handler) {
        handler.onDataLoadingEvent(this);
    }

}
