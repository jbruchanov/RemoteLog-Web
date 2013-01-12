package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TabPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.presenter.TabDevicesPresenter.OnDetailClickListener;
import com.scurab.gwt.rlw.client.presenter.TabDevicesPresenter.OnDeviceSelectionChangeListener;
import com.scurab.gwt.rlw.client.view.ContentView;
import com.scurab.gwt.rlw.shared.model.Device;

public class ContentViewPresenter extends BasePresenter implements IsWidget, OnDeviceSelectionChangeListener {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private ContentView mDisplay;
    private String mApp;

    private TabSettingsPresenter mSettingsPresenter;
    private TabLogsPresenter mLogPresenter;
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
        if(mApp != null){
            mSettingsPresenter = new TabSettingsPresenter(mDataService, mEventBus, mApp, mDisplay.getSettingsPanel());
            mSettingsPresenter.onLoadingSettings();
        }
        
        mLogPresenter = new TabLogsPresenter(mDataService, mEventBus, mApp, mDisplay.getLogsPanel());
        mLogPresenter.onLoadData(0);
        mDevicesPresenter = new TabDevicesPresenter(mDataService, mEventBus, mApp, mDisplay.getDevicesPanel());
        mDevicesPresenter.onLoadData(0);
        
        mDevicePresenter = new TabDevicePresenter(mDataService, mEventBus, mApp, mDisplay.getDevicePanel());
        
        
        TabPanel tb = mDisplay.getTabPanel();
        tb.getTabBar().setTabEnabled(tb.getWidgetCount()-1, false);
        mDevicesPresenter.setSelectionListener(this);
        
        if(mApp == null){
            tb.remove(0);//remove first one, because we are in All tab
        }
    }
    
    private void bind(){
        mDevicesPresenter.setDetailClickListener(new OnDetailClickListener() {
            @Override
            public void onClick(Device d) {
                enableAndselectDeviceTab(true, true);
            }
        });
    }
    
    private void enableAndselectDeviceTab(boolean enable, boolean select){
        TabPanel tb = mDisplay.getTabPanel();
        int lastTabIndex = tb.getWidgetCount()-1;
        tb.getTabBar().setTabEnabled(lastTabIndex, enable); 
        if(select){
            tb.selectTab(tb.getWidgetCount()-1);
        }
    }

    @Override
    public void onSelectionChange(Device d) {
        mDevicePresenter.onSelectionChange(d);
        enableAndselectDeviceTab(d != null, false);
    }
}
