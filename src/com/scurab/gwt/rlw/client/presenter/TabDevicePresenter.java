package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.components.DeviceTableWidget;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.components.LazyPager;
import com.scurab.gwt.rlw.client.dialog.DeviceFilterDialog;
import com.scurab.gwt.rlw.client.dialog.FilterDialog;
import com.scurab.gwt.rlw.client.interfaces.DownloadFinishListener;
import com.scurab.gwt.rlw.shared.model.Device;

public class TabDevicePresenter extends TabBasePresenter<Device> {

    private DynamicTableWidget mDevicesTable;
    private FilterDialog mDeviceFilterDialog;
    
    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private String mApp;
    private HTMLPanel mLogPanel;

    public TabDevicePresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName, HTMLPanel tabPanel) {
        super(dataService, eventBus, appName, tabPanel);
        mDataService = dataService;
        mEventBus = eventBus;
        mApp = appName;
        mLogPanel = tabPanel;
        loadDevices(0);
    }

    @Override
    protected List<HashMap<String, Object>> transformData(List<Device> data) {
        List<HashMap<String, Object>> rCollection = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < data.size(); i++) {
            Device d = data.get(i);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("UUID_1", d.getDevUUID());
            result.put("Brand_2", d.getBrand());
            result.put("Model_3", d.getModel());
            result.put("Platform_4", d.getPlatform());
            result.put("Version_5", d.getVersion());
            rCollection.add(result);
        }
        return rCollection;
    }

    private void loadDevices(int page) {
        notifyLoadingDataStart(WORDS.LoadingDevices());
        mDataService.getDevices(createParams(page).toString(), new AsyncCallback<List<Device>>() {
            @Override
            public void onSuccess(List<Device> result) {
                onLoadDevices(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                notifyLoadingDataStop();
            }
        });
    }

    
    
    protected void setDeviceFilter(HashMap<String, Object> data){
        if(mDevicesTable != null){
            mDevicesTable.removeFromParent();        
            mDevicesTable = null;
            loadDevices(0);
        }
    }

    
    protected void onLoadDevices(List<Device> result) {
        List<HashMap<String, Object>> transformed = transformData(result);
        if (mDevicesTable == null) {
            // init table
            mDevicesTable = new DeviceTableWidget();
            mDevicesTable.setData(transformed);
            mDevicesTable.getFilterButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onDeviceFilterClick();
                }
            });
            mDevicesTable.getFilterCheckBox().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onDeviceFilterChange(((CheckBox)event.getSource()).getValue(), null);
                }
            });
            mLogPanel.add(mDevicesTable);            
            // init lazy loader
            mDevicesTable.setLoadListener(new LazyPager.OnPageLoaderListener() {
                @Override
                public void onLoadPage(int page, final DownloadFinishListener c) {
                    notifyLoadingDataStart(WORDS.LoadingDevices());
                    mDataService.getDevices(createParams(page).toString(), new AsyncCallback<List<Device>>() {
                        @Override
                        public void onSuccess(List<Device> result) {
                            int records = result != null ? result.size() : 0;
                            onLoadDevices(result);
                            c.onDownlodFinish(records);
                        }

                        @Override
                        public void onFailure(Throwable caught) {
                            notifyLoadingDataStop();
                            Window.alert(caught.getMessage());
                            c.onDownlodFinish(-1);
                        }
                    });
                }
            });

        } else {
            mDevicesTable.addData(transformed);
        }
        notifyLoadingDataStop();
    }
    
    protected void onDeviceFilterChange(Boolean value, HashMap<String, Object> filters) {
        if (value == true) {
            if (filters == null && mDeviceFilterDialog != null) {
                setDeviceFilter(filters);
            } else if (filters != null) {
                setDeviceFilter(filters);
            }
        } else {
            setDeviceFilter(null);
        }
    }

    protected void onDeviceFilterClick(){
        if(mDeviceFilterDialog == null){
            mDeviceFilterDialog = new DeviceFilterDialog(mApp, mDataService, new FilterDialog.OnOkListener() {
                @Override
                public void onClickOk(FilterDialog source, HashMap<String, Object> filters) {
                    if(filters.size() > 0){
                        onDeviceFilterChange(true, filters);
                    }
                }
            });
        }
        mDeviceFilterDialog.show();
    }
}
