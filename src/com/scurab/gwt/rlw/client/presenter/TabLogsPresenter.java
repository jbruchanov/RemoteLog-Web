package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.components.LogItemsTableWidget;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget.OnActionCellEventListener;
import com.scurab.gwt.rlw.client.dialog.FilterDialog;
import com.scurab.gwt.rlw.client.dialog.FilterDialog.OnOkListener;
import com.scurab.gwt.rlw.client.dialog.LogFilterDialog;
import com.scurab.gwt.rlw.client.interfaces.IsSelectable;
import com.scurab.gwt.rlw.client.presenter.TabDevicesPresenter.OnDeviceSelectionChangeListener;
import com.scurab.gwt.rlw.shared.TableColumns;
import com.scurab.gwt.rlw.shared.model.LogItem;

public class TabLogsPresenter extends TabDataPresenter<LogItem> {

    private LogItemsTableWidget mLogTable;
    private LogFilterDialog mFilterDialog;
    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private String mApp;
    private HTMLPanel mLogPanel;
    private OnDeviceSelectionChangeListener mListener;

    public TabLogsPresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName, HTMLPanel tabPanel) {
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
            if(mApp == null){
                result.put("Application_2", d.getApplication());
            }
            result.put("Version_3", d.getAppVersion());
            result.put("Build_4", d.getAppBuild());
//            result.put("Date_5", RemoteLogWeb.DATETIMEFORMAT.format(d.getDate()));
            result.put("Date_5", d.getDateText());
            result.put("Category_6", d.getCategory());
            result.put("Source_7", d.getSource());
            result.put("Message_8", d.getMessage());
            result.put(TableColumns.LogBlobMime, d.getBlobMime());
            result.put(TableColumns.LogDeviceID, d.getDeviceID());
            rCollection.add(result);
        }
        return rCollection;
    }

    @Override
    protected DynamicTableWidget onCreateTable() {
        mLogTable = new LogItemsTableWidget();
        mLogTable.setDeviceButtonListener(new OnActionCellEventListener() {
            @Override
            public void onEvent(Delegate<HashMap<String, Object>> delegate, HashMap<String, Object> object) {
                try{
                    Integer id  = (Integer)object.get(TableColumns.LogDeviceID);
                    onOpenDevice(id);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
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
    
    public void onOpenDevice(int id){
        if(mListener != null){
            mListener.onSelectionChange(id);
        }
    }
    
    public void setDeviceButtonListener(OnDeviceSelectionChangeListener listener) {
        mListener = listener;
    }      
}
