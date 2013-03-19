package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.interfaces.IsSelectable;
import com.scurab.gwt.rlw.client.presenter.TabDevicesPresenter.OnDeviceSelectionChangeListener;
import com.scurab.gwt.rlw.client.view.DevicePanel;
import com.scurab.gwt.rlw.shared.model.Device;

public class TabDevicePresenter extends TabBasePresenter implements OnDeviceSelectionChangeListener, IsSelectable {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private HTMLPanel mContainer;
    private DevicePanel mDevicePanel;
    private String mAppName;
    
    private Device mDevice;
    
    private TabSettingsPresenter mSettingsPresenter;
    private DeviceDetailPresenter mDeviceDetailPresenter;
    private PushMessagesPresenter mMessagePresenter;
    
    private boolean mIsSelected;
    
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
        
        if(mAppName != null){
            mSettingsPresenter = new TabSettingsPresenter(mDataService, mEventBus, mAppName, mDevicePanel.getSettingsPanel());
        }
        mDeviceDetailPresenter = new DeviceDetailPresenter(mDataService, mEventBus, mAppName, mDevicePanel.getDevicePanel());
        mMessagePresenter = new PushMessagesPresenter(mDataService, mEventBus, mAppName, mDevicePanel.getMessagesPanel());
    }
    
    public void setDevice(Device d){
        mDevice = d;
        if(isSelected()){
            onLoadSettings(d);
        }
        mDeviceDetailPresenter.setDevice(mDevice);
        mMessagePresenter.setDevice(mDevice);       
    }
    
    public void onLoadSettings(Device d){
        if(mSettingsPresenter != null && d != null){
            mSettingsPresenter.setDevice(d);
        }
    }
    
    public Device getDevice(){
        return mDevice;
    }

    @Override
    public void onSelectionChange(Device d) {
        setDevice(d);
    }

    @Override
    public void onSelectionChange(int id) {
        //ignore here
    }
    
    @Override
    public void setSelected(boolean selected) {
        mIsSelected = selected;
        if(mIsSelected){
            onLoadSettings(mDevice);
        }
    }

    @Override
    public boolean isSelected() {
        return mIsSelected;
    }
}
