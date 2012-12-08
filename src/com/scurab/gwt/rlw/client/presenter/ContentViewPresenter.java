package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.components.DeviceTableWidget;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.components.LazyPager;
import com.scurab.gwt.rlw.client.interfaces.DownloadFinishListener;
import com.scurab.gwt.rlw.client.view.ContentView;
import com.scurab.gwt.rlw.shared.model.Device;

public class ContentViewPresenter extends BasePresenter implements IsWidget {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private ContentView mDisplay;
    private String mApp;

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
        loadDevices(0);
    }

    private void loadDevices(int page) {
        notifyLoadingDataStart(WORDS.LoadingDevices());
        mDataService.getDevices(mApp, page, new AsyncCallback<List<Device>>() {
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

    private DynamicTableWidget mTable;

    protected void onLoadDevices(List<Device> result) {
        List<HashMap<String, Object>> transformed = transform(result);
        if (mTable == null) {
            // init table
            mTable = new DeviceTableWidget();
            mTable.setData(transformed);
            mDisplay.getDevicesPanel().add(mTable);
            // init lazy loader
            mTable.setLoadListener(new LazyPager.OnPageLoaderListener() {
                @Override
                public void onLoadPage(int page, final DownloadFinishListener c) {
                    notifyLoadingDataStart(WORDS.LoadingDevices());
                    mDataService.getDevices(mApp, page, new AsyncCallback<List<Device>>() {
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
            mTable.addData(transformed);
        }
        notifyLoadingDataStop();
    }

    private List<HashMap<String, Object>> transform(List<Device> data) {
        List<HashMap<String, Object>> rCollection = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < data.size(); i++) {
            Device d = data.get(i);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("UUID_1", d.getDevUUID());
            result.put("Brand_2", d.getBrand());
            result.put("Model_3", d.getModel());
            result.put("Platform_4", d.getPlatform());
            result.put("Version_5", d.getVersion());
            result.put("Resolution_6", d.getResolution());
            result.put("Description_7", d.getDescription());
            result.put("Detail_8", d.getDetail());
            rCollection.add(result);
        }
        return rCollection;
    }
}
