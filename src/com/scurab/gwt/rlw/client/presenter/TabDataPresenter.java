package com.scurab.gwt.rlw.client.presenter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.RemoteLogWeb;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.components.LazyPager;
import com.scurab.gwt.rlw.client.dialog.FilterDialog;
import com.scurab.gwt.rlw.client.interfaces.DownloadFinishListener;

public abstract class TabDataPresenter<T> extends TabBasePresenter {

    private DynamicTableWidget mTable;

    private HTMLPanel mContentPanel;

    private FilterDialog mFilterDialog;

    private LazyPager.OnPageLoaderListener mBigLoadListener;

    public TabDataPresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName, HTMLPanel tabPanel) {
        super(dataService, eventBus, appName, tabPanel);
        mContentPanel = tabPanel;
        onCreate();
    }

    protected void onCreate() {
        mBigLoadListener = new LazyPager.OnPageLoaderListener() {
            @Override
            public void onLoadPage(int page, final DownloadFinishListener c) {
                // notify start
                notifyStartDownloading();
                // load data
                onLoadData(page, new AsyncCallback<List<T>>() {
                    @Override
                    public void onSuccess(List<T> result) {
                        int records = result != null ? result.size() : 0;
                        // update view
                        onLoadFinish(result);
                        // dont forget to notify lazypager
                        if (c != null) {
                            c.onDownlodFinish(records);
                        }
                        // notify user
                        notifyStopDownloading();
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        // dont forget to notify lazypager
                        notifyStopDownloading();
                    }
                });
            }
        };
    }

    protected void onLoadFinish(List<T> data) {
        List<HashMap<String, Object>> transformed = transformData(data);
        if (mTable == null) {
            // init table
            mTable = onCreateTable();
            mTable.setData(transformed);
            // set listeners
            dispatchSetFilterListeners(mTable);
            dispatchAddTableToPanel(mTable);
            dispatchSetLazyLoadListener(mTable);
            dispatchSetReloadButtonListener(mTable);
        } else {
            mTable.addData(transformed);
        }
    }

    protected void dispatchSetReloadButtonListener(DynamicTableWidget table) {
        mTable.getReloadButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                dispatchReloadData((Button)event.getSource());
            }
        });
    }

    protected void dispatchAddTableToPanel(DynamicTableWidget table) {
        getContentPanel().add(table);
    }

    protected void dispatchSetFilterListeners(DynamicTableWidget table) {
        mTable.getFilterButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onLogFilterClick();
            }
        });
        mTable.getFilterCheckBox().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onFilterCheckBoxChange(((CheckBox) event.getSource()));
            }
        });
    }

    protected void onLogFilterClick() {
        // init filter dialog
        if (mFilterDialog == null) {
            mFilterDialog = onCreateFilterDialog(new FilterDialog.OnOkListener() {
                @Override
                public void onClickOk(FilterDialog source, HashMap<String, Object> filters) {
                    if (filters != null) {
                        CheckBox cb = mTable.getFilterCheckBox();
                        // set checkbox true/false according to filter size
                        cb.setValue(filters.size() > 0);
                        // notify about change
                        onFilterCheckBoxChange(cb);
                    }
                }
            });
        }
        mFilterDialog.show();
    }

    protected void dispatchSetLazyLoadListener(DynamicTableWidget table) {
        table.setLoadListener(mBigLoadListener);
    }

    protected void onFilterCheckBoxChange(CheckBox source) {
        if (source.getValue() != null) {// probably not necessary check
            if (mFilterDialog != null) {// only if there is any filter
                dispatchReloadData(source);
            }else{
                // keep checbox unchecked if no filter defined
                source.setValue(false);
            }
        }
    }
    
    protected void dispatchReloadData(final FocusWidget initiator){
        initiator.setEnabled(false);
        // load data
        notifyStartDownloading();
        onLoadData(0, new AsyncCallback<List<T>>() {
            @Override
            public void onFailure(Throwable caught) {
                // notify
                // TODO: better
                Window.alert(caught.getMessage());
                notifyStopDownloading();
                // reenable initiator
                initiator.setEnabled(true);
                
                // if checkbox switch value to previous one
                if(initiator instanceof CheckBox){
                    CheckBox cb = (CheckBox)initiator;
                    cb.setValue(!cb.getValue());
                }                
            }

            @Override
            public void onSuccess(List<T> result) {
                if (result != null) {// if it's null some problem is on server
                    mTable.setData(transformData(result));
                }
                notifyStopDownloading();
                // reenable initiator
                initiator.setEnabled(true);
            }
        });
    }

    protected abstract DynamicTableWidget onCreateTable();

    /**
     * Transform data for DynamicTable
     * 
     * @param data
     * @return
     */
    protected abstract List<HashMap<String, Object>> transformData(List<T> data);

    /**
     * Create FilterDialog
     * 
     * @param okListener
     *            - pass oklistener to filterdialog to handle OK click by this class
     * @return
     */
    protected abstract FilterDialog onCreateFilterDialog(FilterDialog.OnOkListener okListener);

    /**
     * Load page<br/>
     * Should be called only after object creation with page 0
     * 
     * @param page
     */
    public void onLoadData(int page) {
        mBigLoadListener.onLoadPage(page, null);
    }

    /**
     * 
     * @param page
     * @param filter
     *            optional value for filter, can be null
     * @param listener
     */
    protected abstract void onLoadData(int page, AsyncCallback<List<T>> listener);

    /**
     * Make something for notification user about start
     */
    protected abstract void notifyStartDownloading();

    /**
     * Returns hashmap if filter is enabled, otherwise null
     * 
     * @return
     */
    protected HashMap<String, Object> getFilter() {
        Boolean b = mTable != null ? mTable.getFilterCheckBox().getValue() : false;
        if (b != null && b == true) {
            return mFilterDialog.getFilterValues();
        }
        return null;
    }

    @Override
    protected JSONObject createParams(int page) {
        JSONObject obj = super.createParams(page);
        // add filter params
        HashMap<String, Object> values = getFilter();
        if (values != null) {
            for (String key : values.keySet()) {
                Object o = values.get(key);
                String v = String.valueOf(o);
                if ("null".equalsIgnoreCase(v)) {
                    obj.put(key, JSONNull.getInstance());
                } else if (o instanceof Number) {
                    obj.put(key, new JSONNumber(((Number) o).doubleValue()));
                } else if (o instanceof Date) {
                    // 2012-12-09 18:30:36.830
                    // by Gson is deserialized as date
                    String f = RemoteLogWeb.DATETIMEFORMAT.format((Date) o);
                    obj.put(key, new JSONString(f));
                } else {

                    obj.put(key, new JSONString(v));
                }
            }
        }
        return obj;
    }

    public HTMLPanel getContentPanel() {
        return mContentPanel;
    }
}
