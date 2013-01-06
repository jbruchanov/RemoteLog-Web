package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.IsWidget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.presenter.TabDevicesPresenter.OnDetailClickListener;
import com.scurab.gwt.rlw.client.view.ContentView;
import com.scurab.gwt.rlw.shared.model.Device;

public class ContentViewPresenter extends BasePresenter implements IsWidget {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private ContentView mDisplay;
    private String mApp;

    private TabLogPresenter mLogPresenter;
    private TabDevicesPresenter mDevicesPresenter;
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
        bind();
    }

    private void init() {
        mLogPresenter = new TabLogPresenter(mDataService, mEventBus, mApp, mDisplay.getLogsPanel());
        mLogPresenter.onLoadData(0);
        mDevicesPresenter = new TabDevicesPresenter(mDataService, mEventBus, mApp, mDisplay.getDevicesPanel());
        mDevicesPresenter.onLoadData(0);
        
        mDevicePresenter = new TabDevicePresenter(mDataService, mEventBus, mApp, mDisplay.getDevicePanel());
        
        mDevicesPresenter.setSelectionListener(mDevicePresenter);
        
    }
    
    private void bind(){
        mDevicesPresenter.setDetailClickListener(new OnDetailClickListener() {
            @Override
            public void onClick(Device d) {
                mDisplay.setCurrentTab(2);
            }
        });
    }
}
