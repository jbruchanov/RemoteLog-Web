package com.scurab.gwt.rlw.client.dialog;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.shared.QueryNames;
import com.scurab.gwt.rlw.shared.SharedParams;

public class DeviceFilterView extends Composite implements IsFilterWidget{

    private static DeviceFilterViewUiBinder uiBinder = GWT.create(DeviceFilterViewUiBinder.class);
    @UiField TextBox mUUID;
    @UiField ListBox mBrand;
    @UiField ListBox mModel;
    @UiField ListBox mPlatform;
    @UiField ListBox mResolution;
    @UiField Button mOkButton;
    @UiField Button mCancelButton;
    
    private DataServiceAsync mDataService;
    
    private String mApplication;

    interface DeviceFilterViewUiBinder extends UiBinder<Widget, DeviceFilterView> {
    }

    public DeviceFilterView(String appName, DataServiceAsync dataService) {
        initWidget(uiBinder.createAndBindUi(this));
        mDataService = dataService;
        mApplication = appName;
        initData();
    }
    
    
    private void initData(){
        
        mDataService.getDistinctValues(mApplication, mApplication == null ? QueryNames.SELECT_BRANDS
                : QueryNames.SELECT_BRANDS_BY_APPNAME, new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                fillListBox(mBrand, result);
            }
            @Override public void onFailure(Throwable caught) {Window.alert(caught.getMessage());}
        });
        
        mDataService.getDistinctValues(mApplication, mApplication == null ? QueryNames.SELECT_MODELS
                : QueryNames.SELECT_MODELS_BY_APPNAME, new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                fillListBox(mModel, result);
            }
            @Override public void onFailure(Throwable caught) {Window.alert(caught.getMessage());}
        });
        
        mDataService.getDistinctValues(mApplication, mApplication == null ? QueryNames.SELECT_PLATFORMS
                : QueryNames.SELECT_PLATFORMS_BY_APPNAME, new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                fillListBox(mPlatform, result);
            }
            @Override public void onFailure(Throwable caught) {Window.alert(caught.getMessage());}
        });
        
        mDataService.getDistinctValues(mApplication, mApplication == null ? QueryNames.SELECT_RESOLUTIONS
                : QueryNames.SELECT_RESOLUTIONS_BY_APPNAME, new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                fillListBox(mResolution, result);
            }
            @Override public void onFailure(Throwable caught) {Window.alert(caught.getMessage());}
        });
    }
    
    private void fillListBox(ListBox lb, List<String> values){
        lb.clear();
        lb.addItem("", "");
        if(values != null && values.size() > 0){
            for(String s : values){
                lb.addItem(s,s);
            }
        }
    }
    public TextBox getUUID() {
        return mUUID;
    }

    public ListBox getBrand() {
        return mBrand;
    }

    public ListBox getModel() {
        return mModel;
    }

    public ListBox getPlatform() {
        return mPlatform;
    }

    public ListBox getResolution() {
        return mResolution;
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
}