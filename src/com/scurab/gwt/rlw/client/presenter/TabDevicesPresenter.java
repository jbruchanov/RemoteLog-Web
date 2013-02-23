package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.components.DeviceTableWidget;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget.OnActionCellEventListener;
import com.scurab.gwt.rlw.client.dialog.DeviceFilterDialog;
import com.scurab.gwt.rlw.client.dialog.FilterDialog;
import com.scurab.gwt.rlw.client.dialog.FilterDialog.OnOkListener;
import com.scurab.gwt.rlw.shared.TableColumns;
import com.scurab.gwt.rlw.shared.model.Device;

public class TabDevicesPresenter extends TabDataPresenter<Device> {

    private DeviceTableWidget mDevicesTable;
    private DeviceFilterDialog mFilterDialog;
    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private String mApp;
    private HTMLPanel mLogPanel;
    
    private HashMap<Integer, Device> mLoadedData;
    
    public interface OnDeviceSelectionChangeListener{
        void onSelectionChange(Device d);
        void onSelectionChange(int id);
    }
    private OnDeviceSelectionChangeListener mSelectionListener;
    
    public interface OnDetailClickListener{
        void onClick(Device d);
    }
    private OnDetailClickListener mDetailClickListener;

    public TabDevicesPresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName, HTMLPanel tabPanel) {
        super(dataService, eventBus, appName, tabPanel);
        mDataService = dataService;
        mEventBus = eventBus;
        mApp = appName;
        mLogPanel = tabPanel;
        mLoadedData = new HashMap<Integer, Device>();
    }

    @Override
    protected List<HashMap<String, Object>> transformData(List<Device> data) {
        List<HashMap<String, Object>> rCollection = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < data.size(); i++) {
            Device d = data.get(i);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put(TableColumns.DeviceID, d.getDeviceID());
            result.put("AppV_01", d.getAppVersion());
            result.put("UUID_02", d.getDevUUID());
            result.put("Owner_03", d.getOwner());
            result.put("Brand_04", d.getBrand());
            result.put("Model_05", d.getModel());
            result.put("Platform_06", d.getPlatform());
            result.put("OSv_07", d.getVersion());
            result.put("Resolution_08", d.getResolution());
            result.put("Created_09", d.getCreatedText());
            result.put("Updated_10", d.getUpdatedText());
            if(mApp == null){
                result.put("App_11", d.getApp());
            }
            rCollection.add(result);
            
            mLoadedData.put(d.getDeviceID(), d);
        }
        return rCollection;
    }

    @Override
    protected DynamicTableWidget onCreateTable() {
        mDevicesTable = new DeviceTableWidget();
        mDevicesTable.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                SingleSelectionModel<HashMap<String, Object>> selection = (SingleSelectionModel<HashMap<String, Object>>) event.getSource();
                HashMap<String, Object> selected = selection.getSelectedObject();
                onDeviceSelectionChange(selected != null ? getDevice(selected) : null);
            }
        });
        mDevicesTable.setActionCellListener(new OnActionCellEventListener() {
            @Override
            public void onEvent(Delegate<HashMap<String, Object>> delegate, HashMap<String, Object> object) {
                if(mDetailClickListener != null){
                    mDetailClickListener.onClick(getDevice(object));
                }
            }
        });
        return mDevicesTable;
    }
    
    private Device getDevice(HashMap<String, Object> transformed){
        if(transformed != null){
            int id = (Integer) transformed.get(TableColumns.DeviceID);
            return mLoadedData.get(id);
        }else{
            return null;
        }
    }
    
    protected void onDeviceSelectionChange(Device d){
        if(mSelectionListener != null){
            mSelectionListener.onSelectionChange(d);
        }
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

    public void setSelectionListener(OnDeviceSelectionChangeListener listener) {
        mSelectionListener = listener;
    }
    
    public void setDetailClickListener(OnDetailClickListener listener){
        mDetailClickListener = listener;    
    }
    
    @Override
    protected void dispatchReloadData(FocusWidget initiator) {
        deselectDeviceInTable();
        super.dispatchReloadData(initiator);
    }
    
    private void deselectDeviceInTable(){
        SingleSelectionModel<HashMap<String,Object>> sm = (SingleSelectionModel<HashMap<String, Object>>) mDevicesTable.getSelectionModel();
        HashMap<String,Object> selected = sm.getSelectedObject();
        if(selected != null){
            sm.setSelected(selected, false);
        }
        onDeviceSelectionChange(null);
    }
    
    public Device getDevice(Integer id){
        if(id != null){
            return mLoadedData.get(id);
        }
        return null;
    }
}
