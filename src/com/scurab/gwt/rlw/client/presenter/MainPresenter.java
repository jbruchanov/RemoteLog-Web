package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.dom.client.Element;
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
import com.scurab.gwt.rlw.client.controls.MainMenuLink;
import com.scurab.gwt.rlw.client.dialog.DeviceFilterDialog;
import com.scurab.gwt.rlw.client.dialog.FilterDialog;
import com.scurab.gwt.rlw.client.events.DataLoadingEvent;
import com.scurab.gwt.rlw.client.events.DataLoadingEventHandler;
import com.scurab.gwt.rlw.client.view.ContentView;
import com.scurab.gwt.rlw.client.view.MainWindow;

public class MainPresenter extends BasePresenter implements IsWidget {

    private MainWindow mWindow;

    private HashMap<String, ContentViewPresenter> mPresenters;

    private ContentViewPresenter mCurrent;

    private List<MainMenuLink> mMenuLinks;

    private DataServiceAsync mDataService;

    public MainPresenter(DataServiceAsync dataService, HandlerManager eventBus, MainWindow display) {
        super(dataService, eventBus, display);
        mWindow = display;
        mDataService = dataService;
        init();
    }

    private void init() {
        mMenuLinks = new ArrayList<MainMenuLink>();
        mPresenters = new HashMap<String, ContentViewPresenter>();

        // add location change listener
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                onLocationChange(event.getValue());
            }
        });
        // register DataLoadingEvent Listener
        mEventBus.addHandler(DataLoadingEvent.TYPE, new DataLoadingEventHandler() {
            @Override
            public void onDataLoadingEvent(DataLoadingEvent event) {
                int what = event.getType();
                switch (what) {
                case DataLoadingEvent.START_LOADING:
                    showProgress(event.getMessageIfNullEmptyString());
                    break;
                case DataLoadingEvent.STOP_LOADING:
                    hideProgress();
                    break;
                }
            }
        });

        // download appliations
        notifyLoadingDataStart(WORDS.Applications());
        mDataService.getApplications(new AsyncCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                onLoadApplications(result);
                notifyLoadingDataStop();
            }

            @Override
            public void onFailure(Throwable caught) {
                notifyLoadingDataStop();
                Window.alert(caught.getMessage());
                onLoadApplications(null);
            }
        });

        mWindow.getTestButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (dfd == null) {
                    dfd = new DeviceFilterDialog(null, mDataService, new DeviceFilterDialog.OnOkListener() {
                        @Override
                        public void onClickOk(FilterDialog source, HashMap<String, Object> filters) {
                            Window.alert(filters.size() + "");
                        }
                    });
                }
                dfd.show();
            }
        });
    }

    DeviceFilterDialog dfd;

    /**
     * Show progress dialog
     * 
     * @param msg
     *            - notify user what is it about
     */
    protected void showProgress(String msg) {
        mWindow.getProgressBar().setVisible(true);
        mWindow.getStatusBarLabel().setText(msg);
    }

    /**
     * Hide progress dialog
     */
    protected void hideProgress() {
        mWindow.getProgressBar().setVisible(false);
        mWindow.getStatusBarLabel().setText("");
    }

    private void initMenuClickHandlers() {
        for (MainMenuLink mml : mMenuLinks) {
            mml.addHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onMenuItemClick((MainMenuLink) event.getSource());
                }
            }, ClickEvent.getType());
        }
    }

    /*
     * Change style of menu item
     */
    private void onMenuItemClick(MainMenuLink source) {

        for (MainMenuLink h : mMenuLinks) {
            Element e = h.getElement();
            e.setAttribute("class", "mainmenu-item");
        }
        Element e = source.getElement();
        e.setAttribute("class", "mainmenu-selecteditem");
    }

    /**
     * change current view
     * 
     * @param loc
     */
    protected void onLocationChange(String loc) {
        ContentViewPresenter cvp = getPresenter(loc);
        mWindow.getContentPanel().clear();
        mWindow.getContentPanel().add(cvp.asWidget());
        mCurrent = cvp;
    }

    /**
     * Get or create cached presenter
     * 
     * @param name
     * @return
     */
    protected ContentViewPresenter getPresenter(String name) {
        ContentViewPresenter cvp = mPresenters.get(name);
        if (cvp == null) {
            String v = WORDS.All().equals(name) ? null : name;
            cvp = new ContentViewPresenter(v, mDataService, mEventBus, new ContentView());
            mPresenters.put(name, cvp);
        }
        return cvp;
    }

    protected void onLoadApplications(List<String> result) {
        // create all link
        MainMenuLink mml = new MainMenuLink();
        mml.setText(WORDS.All());
        mml.setTargetHistoryToken(WORDS.All());
        mMenuLinks.add(mml);
        HTMLPanel container = mWindow.getMenuItemsContainer();
        container.add(mml);

        // add links
        if (result != null) {
            for (String app : result) {
                mml = new MainMenuLink();
                mml.setText(app);
                mml.setTargetHistoryToken(app);
                container.add(mml);
                mMenuLinks.add(mml);
            }
        }
        initMenuClickHandlers();
    }
}
