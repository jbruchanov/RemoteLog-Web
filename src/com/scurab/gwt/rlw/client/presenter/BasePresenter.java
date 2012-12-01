package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.scurab.gwt.rlw.client.DataServiceAsync;

public abstract class BasePresenter implements IsWidget {

    protected DataServiceAsync mDataService;
    protected HandlerManager mEventBus;
    private IsWidget mDisplay;

    public BasePresenter(DataServiceAsync dataService, HandlerManager eventBus, IsWidget display) {
        mDataService = dataService;
        mEventBus = eventBus;
        mDisplay = display;
    }

    @Override
    public Widget asWidget() {
        return mDisplay.asWidget();
    }

}
