package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.controls.MainMenuLink;
import com.scurab.gwt.rlw.client.view.ContentView;
import com.scurab.gwt.rlw.client.view.MainWindow;
import com.scurab.gwt.rlw.shared.model.Device;

public class MainPresenter extends BasePresenter implements IsWidget {

    private MainWindow mWindow;
    private ContentView mContentView;

    public MainPresenter(DataServiceAsync dataService, HandlerManager eventBus, MainWindow display) {
        super(dataService, eventBus, display);
        mWindow = display;
        init();
    }

    private void init() {
        mContentView = new ContentView();
        mWindow.getContentPanel().add(mContentView);

        mContentView.getnLoadDataButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onLoadClick();
            }
        });

        mDataService.getApplications(new AsyncCallback<List<String>>() {

            @Override
            public void onSuccess(List<String> result) {
                onLoadApplications(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                onLoadApplications(null);
            }
        });
    }

    protected void onLoadApplications(List<String> result) {
        MainMenuLink mml = new MainMenuLink();
        mml.setText("All");
        HTMLPanel container = mWindow.getMenuItemsContainer();
        container.add(mml);
        if (result != null) {
            for (String app : result) {
                mml = new MainMenuLink();
                mml.setText(app);
                container.add(mml);
            }
        }
    }

    private void onLoadClick() {
        mDataService.getDevices(new AsyncCallback<List<Device>>() {

            @Override
            public void onSuccess(List<Device> result) {
                onLoadData(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }
        });
    }

    private DynamicTableWidget mTable;

    protected void onLoadData(List<Device> result) {
        List<HashMap<String, Object>> transformed = transform(result);
        if (mTable == null) {
            mTable = new DynamicTableWidget();
            mTable.setData(transformed);
            mContentView.getDevicesPanel().add(mTable);
            
        } else {
            mTable.addData(transformed);
        }
    }

    private List<HashMap<String, Object>> transform(List<Device> data) {
        List<HashMap<String, Object>> rCollection = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < data.size(); i++) {
            Device d = data.get(i);
            HashMap<String, Object> result = new HashMap<String, Object>();
            result.put("ID_1", d.getId());
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
