package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.view.DevicePanel;

public class TabDevicePresenter extends TabBasePresenter {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private HTMLPanel mContainer;
    private DevicePanel mDevicePanel;
    private String mAppName;
    
    private TabMessagesPresenter mMessagePresenter;
    
    public TabDevicePresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName, HTMLPanel tabPanel) {
        super(dataService, eventBus, appName, tabPanel);
        
        mDataService = dataService;
        mEventBus = eventBus;
        mAppName = appName;
        mContainer = tabPanel;
        
        init();
    }
    
    private void init(){
        mDevicePanel = new DevicePanel();
        mContainer.add(mDevicePanel.asWidget());
        
        mMessagePresenter = new TabMessagesPresenter(mDataService, mEventBus, mAppName, mDevicePanel.getMessagesPanel());
    }
    
    public void setDeviceId(){
        
    }
}
