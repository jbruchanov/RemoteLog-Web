package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.presenter.TabDevicesPresenter.OnDeviceSelectionChangeListener;
import com.scurab.gwt.rlw.client.view.DevicePanel;
import com.scurab.gwt.rlw.shared.model.Device;

public class TabDevicePresenter extends TabBasePresenter implements OnDeviceSelectionChangeListener {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private HTMLPanel mContainer;
    private DevicePanel mDevicePanel;
    private String mAppName;
    
    private Device mDevice;
    
    private TabDeviceDetailPresenter mDeviceDetailPresenter;
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
        
        mDeviceDetailPresenter = new TabDeviceDetailPresenter(mDataService, mEventBus, mAppName, mDevicePanel.getDevicePanel());
        mMessagePresenter = new TabMessagesPresenter(mDataService, mEventBus, mAppName, mDevicePanel.getMessagesPanel());
    }
    
    public void setDevice(Device d){
        mDevice = d;
        mDeviceDetailPresenter.setDevice(mDevice);
        mMessagePresenter.setDevice(mDevice);       
    }
    
    public Device getDevice(){
        return mDevice;
    }

    @Override
    public void onSelectionChange(Device d) {
        setDevice(d);
    }
}
