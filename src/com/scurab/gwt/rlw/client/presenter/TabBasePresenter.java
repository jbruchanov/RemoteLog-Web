package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.shared.SharedParams;

public abstract class TabBasePresenter extends BasePresenter {

    private String mApp;

    public TabBasePresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName, HTMLPanel tabPanel) {
        super(dataService, eventBus, tabPanel);
        mApp = appName;
    }

    @Override
    protected JSONObject createParams(int page) {
        JSONObject param = super.createParams(page);
        if (mApp != null) {
            param.put(SharedParams.APP_NAME, new JSONString(mApp));
        }
        return param;
    }
}
