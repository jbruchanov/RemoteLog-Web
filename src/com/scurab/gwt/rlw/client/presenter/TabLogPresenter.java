package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.components.LogItemTableWidget;
import com.scurab.gwt.rlw.client.dialog.FilterDialog;
import com.scurab.gwt.rlw.client.dialog.FilterDialog.OnOkListener;
import com.scurab.gwt.rlw.client.dialog.LogFilterDialog;
import com.scurab.gwt.rlw.shared.TableColumns;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class TabLogPresenter extends TabDataPresenter<LogItem> {

    private DynamicTableWidget mLogTable;
    private LogFilterDialog mFilterDialog;
    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private String mApp;
    private HTMLPanel mLogPanel;

    public TabLogPresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName, HTMLPanel tabPanel) {
        super(dataService, eventBus, appName, tabPanel);
        mDataService = dataService;
        mEventBus = eventBus;
        mApp = appName;
        mLogPanel = tabPanel;
    }

    @Override
    protected List<HashMap<String, Object>> transformData(List<LogItem> data) {
        List<HashMap<String, Object>> rCollection = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < data.size(); i++) {
            LogItem d = data.get(i);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put(TableColumns.LogItemID, d.getID());
            result.put("Application_2", d.getApplication());
            result.put("AppVersion_3", d.getAppVersion());
            result.put("AppBuild_4", d.getAppBuild());
            result.put("Date_5", d.getDate());
            result.put("Category_6", d.getCategory());
            result.put("Message_7", d.getMessage());
            result.put(TableColumns.LogBlobMime, d.getBlobMime());
            result.put(TableColumns.LogDeviceID, d.getDeviceID());
            rCollection.add(result);
        }
        return rCollection;
    }

    @Override
    protected DynamicTableWidget onCreateTable() {
        mLogTable = new LogItemTableWidget();
        return mLogTable;
    }

    @Override
    protected FilterDialog onCreateFilterDialog(OnOkListener okListener) {
        mFilterDialog = new LogFilterDialog(mApp, mDataService, okListener);
        return mFilterDialog;
    }

    @Override
    protected void onLoadData(int page, AsyncCallback<List<LogItem>> listener) {
        mDataService.getLogs(createParams(page).toString(), listener);
    }

    @Override
    protected void notifyStartDownloading() {
        notifyStartDownloading(WORDS.LoadingLogItems());
    }
}
