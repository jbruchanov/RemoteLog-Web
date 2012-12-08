package com.scurab.gwt.rlw.client;

import com.scurab.gwt.rlw.client.presenter.MainPresenter;
import com.scurab.gwt.rlw.client.view.MainWindow;
import com.scurab.gwt.rlw.language.Words;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RemoteLogWeb implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network " + "connection and try again.";

    public static int PAGE_SIZE = 30;
    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final DataServiceAsync mDataService = GWT.create(DataService.class);
    private final HandlerManager mEventBus = new HandlerManager(null);
    public static final Words WORDS = GWT.create(Words.class);

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        RootPanel.get().add(new MainPresenter(mDataService, mEventBus, new MainWindow()));
    }
}
