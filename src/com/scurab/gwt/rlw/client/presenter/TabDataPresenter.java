package com.scurab.gwt.rlw.client.presenter;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.scurab.gwt.rlw.client.DataServiceAsync;
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
        } else {
            mTable.addData(transformed);
        }
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
        if (mFilterDialog == null) {
            mFilterDialog = onCreateFilterDialog(new FilterDialog.OnOkListener() {
                @Override
                public void onClickOk(FilterDialog source, HashMap<String, Object> filters) {
                    if (filters != null && filters.size() > 0) {
                        CheckBox cb = mTable.getFilterCheckBox();
                        cb.setValue(true);
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

    protected void onFilterCheckBoxChange(final CheckBox source) {
        if (source.getValue() != null) {
            if (mFilterDialog != null) {
                source.setEnabled(false);
                onLoadData(0, new AsyncCallback<List<T>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                        notifyStopDownloading();
                        source.setEnabled(true);
                        source.setValue(!source.getValue());
                    }

                    @Override
                    public void onSuccess(List<T> result) {
                        if (result != null) {
                            mTable.setData(transformData(result));
                        }
                        notifyStopDownloading();
                        source.setEnabled(true);
                    }
                });
            } else {
                // keep checbox unchecked if no filter defined
                source.setValue(false);
            }
        }
    }

    protected abstract DynamicTableWidget onCreateTable();

    protected abstract List<HashMap<String, Object>> transformData(List<T> data);

    protected abstract FilterDialog onCreateFilterDialog(FilterDialog.OnOkListener okListener);

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
                String v = String.valueOf(values.get(key));
                if ("null".equalsIgnoreCase(v)) {
                    obj.put(key, JSONNull.getInstance());
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
