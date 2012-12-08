package com.scurab.gwt.rlw.client.dialog;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.shared.QueryNames;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.Label;

public class LogFilterView extends Composite implements IsFilterWidget {

    private static LogFilterViewUiBinder uiBinder = GWT.create(LogFilterViewUiBinder.class);
    @UiField TextBox mAppBuild;
    @UiField TextBox mAppVersion;
    @UiField ListBox mDataType;
    @UiField ListBox mCategory;
    @UiField IntegerBox mDeviceID;
    @UiField DateBox mDate;
    @UiField TextBox mMessage;
    @UiField Button mOkButton;
    @UiField Button mCancelButton;

    interface LogFilterViewUiBinder extends UiBinder<Widget, LogFilterView> {
    }

    private DataServiceAsync mDataService;
    
    private String mApplication;

    public LogFilterView(String appName, DataServiceAsync dataService) {
        initWidget(uiBinder.createAndBindUi(this));
        mDataService = dataService;
        mApplication = appName;
        mDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
        initData();
    }

    private void initData() {
        mDataService.getDistinctValues(mApplication, mApplication == null ? QueryNames.SELECT_DATATYPES
                : QueryNames.SELECT_DATATYPES_BY_APPNAME, new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                fillListBox(mDataType, result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        });
        
        mDataService.getDistinctValues(mApplication, mApplication == null ? QueryNames.SELECT_CATEGORIES
                : QueryNames.SELECT_CATEGORIES_BY_APPNAME, new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                fillListBox(mCategory, result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        });
    }

    private void fillListBox(ListBox lb, List<String> values) {
        lb.clear();
        lb.addItem("", "");
        if (values != null && values.size() > 0) {
            for (String s : values) {
                lb.addItem(s, s);
            }
        }
    }

    @Override
    public Button getOkButton() {
        return mOkButton;
    }

    @Override
    public Button getCancelButton() {
        return mCancelButton;
    }
    
    public void refreshData(){
        initData();
    }

    public TextBox getAppBuild() {
        return mAppBuild;
    }

    public TextBox getAppVersion() {
        return mAppVersion;
    }

    public ListBox getDataType() {
        return mDataType;
    }

    public ListBox getCategory() {
        return mCategory;
    }

    public IntegerBox getDeviceID() {
        return mDeviceID;
    }

    public DataServiceAsync getDataService() {
        return mDataService;
    }

    public String getApplication() {
        return mApplication;
    }
}
