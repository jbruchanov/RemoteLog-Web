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
import com.scurab.gwt.rlw.client.dialog.FilterDialog.OnOkListener;
import com.scurab.gwt.rlw.client.interfaces.DownloadFinishListener;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class TabDevicePresenter extends TabDataPresenter<Device> {

    private DynamicTableWidget mDevicesTable;
    private DeviceFilterDialog mFilterDialog;
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

    @Override
    protected DynamicTableWidget onCreateTable() {
        mDevicesTable = new DeviceTableWidget();
        return mDevicesTable;
    }

    @Override
    protected FilterDialog onCreateFilterDialog(OnOkListener okListener) {
        mFilterDialog = new DeviceFilterDialog(mApp, mDataService, okListener);
        return mFilterDialog;
    }

    @Override
    protected void onLoadData(int page, AsyncCallback<List<Device>> listener) {
        mDataService.getDevices(createParams(page).toString(), listener);
    }

    @Override
    protected void notifyStartDownloading() {
        notifyStartDownloading(WORDS.LoadingDevices());        
    }
}
