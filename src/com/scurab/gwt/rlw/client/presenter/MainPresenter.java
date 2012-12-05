package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
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
    private HashMap<String, ContentViewPresenter> mPresenters;
    private ContentViewPresenter mCurrent;

    public MainPresenter(DataServiceAsync dataService, HandlerManager eventBus, MainWindow display) {
        super(dataService, eventBus, display);
        mWindow = display;
        init();
    }

    private void init() {
        mPresenters = new HashMap<String, ContentViewPresenter>();
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

        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {    
                onLocationChange(event.getValue());
            }
        });
    }

    protected void onLocationChange(String loc) {
        ContentViewPresenter cvp = getPresenter(loc);
        mWindow.getContentPanel().clear();
        mWindow.getContentPanel().add(cvp.asWidget());
        mCurrent = cvp;
    }

    private ContentViewPresenter getPresenter(String name) {
        ContentViewPresenter cvp = mPresenters.get(name);
        if(cvp == null){
            String v = "0".equals(name) ? null : name;
            cvp = new ContentViewPresenter(v, mDataService, mEventBus, new ContentView());
            mPresenters.put(name, cvp);
        }
        return cvp;
    }

    protected void onLoadApplications(List<String> result) {
        MainMenuLink mml = new MainMenuLink();
        mml.setText("All");
        mml.setTargetHistoryToken("0");
        HTMLPanel container = mWindow.getMenuItemsContainer();
        container.add(mml);
        if (result != null) {
            for (String app : result) {
                mml = new MainMenuLink();
                mml.setText(app);
                mml.setTargetHistoryToken(app);
                container.add(mml);
            }
        }
    }
}
