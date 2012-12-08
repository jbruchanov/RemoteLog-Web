package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.components.DeviceTableWidget;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.components.LazyPager;
import com.scurab.gwt.rlw.client.interfaces.DownloadFinishListener;
import com.scurab.gwt.rlw.client.view.ContentView;
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class ContentViewPresenter extends BasePresenter implements IsWidget {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private ContentView mDisplay;
    private String mApp;
    private DynamicTableWidget mDevicesTable;
    private DynamicTableWidget mLogTable;

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
        loadLogs(0); 
    }

    private void loadDevices(int page) {
        notifyLoadingDataStart(WORDS.LoadingDevices());        
        mDataService.getDevices(createParams(page, mApp).toString(), new AsyncCallback<List<Device>>() {
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
    
    private void loadLogs(int page){
        notifyLoadingDataStart(WORDS.LoadingLogItems());        
        mDataService.getLogs(createParams(page, mApp).toString(), new AsyncCallback<List<LogItem>>() {
            @Override
            public void onSuccess(List<LogItem> result) {
                onLoadLogs(result);
                loadDevices(0);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                notifyLoadingDataStop();
            }
        });
    }
    
    protected void onLoadLogs(List<LogItem> result) {
        List<HashMap<String, Object>> transformed = transformLogs(result);
        if (mLogTable == null) {
            // init table
            mLogTable = new DeviceTableWidget();
            mLogTable.setData(transformed);
            mDisplay.getLogsPanel().add(mLogTable);
            // init lazy loader
            mLogTable.setLoadListener(new LazyPager.OnPageLoaderListener() {
                @Override
                public void onLoadPage(int page, final DownloadFinishListener c) {
                    notifyLoadingDataStart(WORDS.LoadingLogItems());
                    mDataService.getLogs(createParams(page, mApp).toString(), new AsyncCallback<List<LogItem>>() {
                        @Override
                        public void onSuccess(List<LogItem> result) {
                            int records = result != null ? result.size() : 0;
                            onLoadLogs(result);
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
            mLogTable.addData(transformed);
        }
        notifyLoadingDataStop();
    }
    
    private List<HashMap<String, Object>> transformLogs(List<LogItem> data) {
        List<HashMap<String, Object>> rCollection = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < data.size(); i++) {
            LogItem d = data.get(i);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("ID_1", d.getId());            
            result.put("Application_2", d.getApplication());
            result.put("AppVersion_3", d.getAppVersion());
            result.put("AppBuild_4", d.getAppBuild());
            result.put("Date_5", d.getDate());
            result.put("Category_6", d.getCategory());
            result.put("Message_7", d.getMesage());
            result.put("DataType_8", d.getBlobMime());
            result.put("DeviceID_9", d.getDeviceId());
            rCollection.add(result);
        }
        return rCollection;
    }
    
    protected void onLoadDevices(List<Device> result) {
        List<HashMap<String, Object>> transformed = transformDevices(result);
        if (mDevicesTable == null) {
            // init table
            mDevicesTable = new DeviceTableWidget();
            mDevicesTable.setData(transformed);
            mDisplay.getDevicesPanel().add(mDevicesTable);
            // init lazy loader
            mDevicesTable.setLoadListener(new LazyPager.OnPageLoaderListener() {
                @Override
                public void onLoadPage(int page, final DownloadFinishListener c) {
                    notifyLoadingDataStart(WORDS.LoadingDevices());
                    mDataService.getDevices(createParams(page, mApp).toString(), new AsyncCallback<List<Device>>() {
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

    private List<HashMap<String, Object>> transformDevices(List<Device> data) {
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
    
    protected JSONObject createParams(int page, String appName) {
        JSONObject param  = super.createParams(page);
        if(appName != null){
            param.put(SharedParams.APP_NAME, new JSONString(appName));
        }
        return param;
    }
}
