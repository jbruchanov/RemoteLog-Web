package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.RemoteLogWeb;
import com.scurab.gwt.rlw.client.events.DataLoadingEvent;
import com.scurab.gwt.rlw.language.Words;
import com.scurab.gwt.rlw.shared.SharedParams;

public abstract class BasePresenter implements IsWidget {

    protected DataServiceAsync mDataService;
    protected HandlerManager mEventBus;
    private IsWidget mDisplay;

    public static final Words WORDS = RemoteLogWeb.WORDS;

    public BasePresenter(DataServiceAsync dataService, HandlerManager eventBus, IsWidget display) {
        mDataService = dataService;
        mEventBus = eventBus;
        mDisplay = display;
    }

    @Override
    public Widget asWidget() {
        return mDisplay.asWidget();
    }

    protected void notifyLoadingDataStart() {
        notifyLoadingDataStart(null);
    }

    protected void notifyLoadingDataStart(String msg) {
        mEventBus.fireEvent(new DataLoadingEvent(DataLoadingEvent.START_LOADING, msg));
    }

    protected void notifyLoadingDataStop() {
        mEventBus.fireEvent(new DataLoadingEvent(DataLoadingEvent.STOP_LOADING));
    }

    // protected HashMap<String,Object> createParams(int page){
    // HashMap<String, Object> params = new HashMap<String, Object>();
    // params.put(SharedParams.PAGE, page);
    // return params;
    // }

    protected com.google.gwt.json.client.JSONObject createParams(int page) {
        JSONObject obj = new JSONObject();
        obj.put(SharedParams.PAGE, new JSONNumber(page));
        return obj;
    }
}
