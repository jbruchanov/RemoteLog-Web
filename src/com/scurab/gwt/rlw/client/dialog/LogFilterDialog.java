package com.scurab.gwt.rlw.client.dialog;

import java.util.HashMap;

import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.shared.SharedParams;

public class LogFilterDialog extends FilterDialog {

    private LogFilterView mFilterView;
    private String mAppName;
    private DataServiceAsync mDataService;
    
    public LogFilterDialog(String appName, DataServiceAsync service, OnOkListener listener) {
        super(appName, service, listener);
        mAppName = appName;
        mDataService = service;
    }

    @Override
    protected IsFilterWidget onCreateView(String appName, DataServiceAsync service) {
        mFilterView = new LogFilterView(appName, service);
        return mFilterView;
    }

    @Override
    public HashMap<String, Object> getFilterValues() {
        HashMap<String, Object> filters = new HashMap<String, Object>();
        insertValue(filters, SharedParams.LOG_APPBUILD, getTextBoxValue(mFilterView.mAppBuild));
        insertValue(filters, SharedParams.LOG_APPVERSION, getTextBoxValue(mFilterView.mAppVersion));
        insertValue(filters, SharedParams.LOG_CATEGORY, getListBoxValue(mFilterView.mCategory));
        insertValue(filters, SharedParams.LOG_DATATYPE, getListBoxValue(mFilterView.mDataType));
        insertValue(filters, SharedParams.LOG_DATE, mFilterView.mDate.getValue());
        insertValue(filters, SharedParams.LOG_DEVICEID, mFilterView.mDeviceID.getValue());
        insertValue(filters, SharedParams.LOG_MESSAGE, getTextBoxValue(mFilterView.mMessage));        
        return filters;
    }

    @Override
    public void refreshData() {
        mFilterView.refreshData();
    }
}
