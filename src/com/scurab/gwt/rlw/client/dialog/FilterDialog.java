package com.scurab.gwt.rlw.client.dialog;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.scurab.gwt.rlw.client.DataServiceAsync;

public abstract class FilterDialog extends DialogBox {

    public interface OnOkListener {
        void onClickOk(FilterDialog source, HashMap<String, Object> filters);
    }

    private String mApp;
    private DataServiceAsync mDataService;
    private IsFilterWidget mFilterWidget;
    private OnOkListener mListener;

    public FilterDialog(String appName, DataServiceAsync service, OnOkListener listener) {
        super(false, true);
        mApp = appName;
        mDataService = service;
        mListener = listener;

        setGlassEnabled(true);
        center();

        mFilterWidget = onCreateView(mApp, mDataService);
        add(mFilterWidget.asWidget());

        mFilterWidget.getCancelButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        mFilterWidget.getOkButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (mListener != null) {
                    mListener.onClickOk(FilterDialog.this, getFilterValues());
                }
                hide();
            }
        });
    }

    protected abstract IsFilterWidget onCreateView(String appName, DataServiceAsync service);

    public abstract HashMap<String, Object> getFilterValues();

    public abstract void refreshData();

    protected void insertValue(HashMap<String, Object> values, String key, Object value) {
        if (value != null && value.toString().length() > 0) {
            values.put(key, value);
        }
    }

    protected String getListBoxValue(ListBox v) {
        String result = v.getValue(v.getSelectedIndex());
        return result.length() != 0 ? result : null;
    }

    protected String getTextBoxValue(TextBoxBase v) {
        String result = v.getValue();
        return result.length() != 0 ? result : null;
    }
}
