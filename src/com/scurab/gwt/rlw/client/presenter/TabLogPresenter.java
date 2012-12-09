package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
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
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class TabLogPresenter extends TabBasePresenter<LogItem> {

    private DynamicTableWidget mLogTable;
    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private String mApp;
    private HTMLPanel mLogPanel;
    private FilterDialog mLogFilterDialog;
    
    public TabLogPresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName, HTMLPanel tabPanel) {
        super(dataService, eventBus, appName, tabPanel);
        mDataService = dataService;
        mEventBus = eventBus;
        mApp = appName;
        mLogPanel = tabPanel;
        init();
    }
    
    private void init(){
        loadLogs(0);
    }
    
    private void loadLogs(int page) {
        notifyLoadingDataStart(WORDS.LoadingLogItems());
        mDataService.getLogs(createParams(page, mApp).toString(), new AsyncCallback<List<LogItem>>() {
            @Override
            public void onSuccess(List<LogItem> result) {
                onLoadLogs(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                notifyLoadingDataStop();
            }
        });
    }

    protected void onLoadLogs(List<LogItem> result) {
        onLoadLogs(result, true);
    }
    protected void onLoadLogs(List<LogItem> result, boolean add) {
        List<HashMap<String, Object>> transformed = transformData(result);
        if (mLogTable == null) {
            // init table
            mLogTable = new DeviceTableWidget();
            mLogTable.setData(transformed);
            mLogTable.getFilterButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onLogFilterClick();
                }
            });
            mLogTable.getFilterCheckBox().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onLogFilterChange(((CheckBox)event.getSource()).getValue(), null);
                }
            });
            mLogPanel.add(mLogTable);
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
            if(add){
                mLogTable.addData(transformed);
            }else{
                mLogTable.setData(transformed);
            }
        }
        notifyLoadingDataStop();
    }

    protected void onLogFilterClick() {
        if(mLogFilterDialog == null){
            mLogFilterDialog = new DeviceFilterDialog(mApp, mDataService, new FilterDialog.OnOkListener() {
                @Override
                public void onClickOk(FilterDialog source, HashMap<String, Object> filters) {
                    if(filters.size() > 0){
                        onLogFilterChange(true, filters);
                    }
                }
            });
        }
        mLogFilterDialog.show();
    }


    protected void onLogFilterChange(Boolean value, HashMap<String, Object> filters) {
        if (value == true) {
            if (filters == null && mLogFilterDialog != null) {
                setLogFilter(filters);
            } else if (filters != null) {
                setLogFilter(filters);
            }
        } else {
            setLogFilter(null);
        }
    }    
    
    protected void setLogFilter(HashMap<String, Object> data){
        if(mLogTable != null){
            mLogTable.clear();
            mLogTable = null;
            loadLogs(0);    
        }
    }
    
    protected JSONObject createParams(int page, String appName) {
        JSONObject param = super.createParams(page);
        if (appName != null) {
            param.put(SharedParams.APP_NAME, new JSONString(appName));
        }
        return param;
    }

    @Override
    protected List<HashMap<String, Object>> transformData(List<LogItem> data) {
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
}
