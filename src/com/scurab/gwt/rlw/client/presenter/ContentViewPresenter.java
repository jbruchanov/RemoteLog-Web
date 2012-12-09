package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.IsWidget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.view.ContentView;

public class ContentViewPresenter extends BasePresenter implements IsWidget {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private ContentView mDisplay;
    private String mApp;

    private TabLogPresenter mLogPresenter;
    private TabDevicePresenter mDevicePresenter;

    public ContentViewPresenter(DataServiceAsync dataService, HandlerManager eventBus, ContentView display) {
        this(null, dataService, eventBus, display);
    }

    public ContentViewPresenter(String appName, DataServiceAsync dataService, HandlerManager eventBus,
            ContentView display) {
        super(dataService, eventBus, display);
        mEventBus = eventBus;
        mDataService = dataService;
        mDisplay = display;
        mApp = appName;
        init();
    }

    private void init() {
        mLogPresenter = new TabLogPresenter(mDataService, mEventBus, mApp, mDisplay.getLogsPanel());
        mLogPresenter.onLoadData(0);
        mDevicePresenter = new TabDevicePresenter(mDataService, mEventBus, mApp, mDisplay.getDevicesPanel());
        mDevicePresenter.onLoadData(0);
    }

}
