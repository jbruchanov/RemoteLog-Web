package com.scurab.gwt.rlw.client;

import java.util.HashMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.scurab.gwt.rlw.client.presenter.MainPresenter;
import com.scurab.gwt.rlw.client.view.MainWindow;
import com.scurab.gwt.rlw.language.Words;
import com.scurab.gwt.rlw.shared.SharedParams;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RemoteLogWeb implements EntryPoint {
    /**
     * The message displayed to the user when the server cannot be reached or returns an error.
     */
    private static final String SERVER_ERROR = "An error occurred while "
            + "attempting to contact the server. Please check your network " + "connection and try again.";

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final DataServiceAsync mDataService = GWT.create(DataService.class);
    private final HandlerManager mEventBus = new HandlerManager(null);
    public static final Words WORDS = GWT.create(Words.class);
    public static int PAGE_SIZE = 50;

    public static DateTimeFormat DATETIMEFORMAT = DateTimeFormat.getFormat("yyyy-MM-dd kk:mm:ss");
    public static final HashMap<String, Object> CLIENT_PROPERTIES = new HashMap<String, Object>();

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        RootPanel.get().add(new MainPresenter(mDataService, mEventBus, new MainWindow()));
        mDataService.getProperties(new AsyncCallback<String>() {
            
            @Override
            public void onSuccess(String result) {
                JSONObject jso = JSONParser.parseStrict(result).isObject();
                for(String key: jso.keySet()){
                    if(jso.get(key).isString() != null){
                        CLIENT_PROPERTIES.put(key, jso.get(key).isString().stringValue());
                    }else if(jso.get(key).isNumber() != null){
                        CLIENT_PROPERTIES.put(key, jso.get(key).isNumber().doubleValue());
                    }
                    else if(jso.get(key).isBoolean() != null){
                        CLIENT_PROPERTIES.put(key, jso.get(key).isBoolean().booleanValue());
                    }
                }
                if(CLIENT_PROPERTIES.containsKey(SharedParams.CLIENT_PAGE_SIZE)){
                    PAGE_SIZE = ((Number)(CLIENT_PROPERTIES.get(SharedParams.CLIENT_PAGE_SIZE))).intValue();    
                }
                if(CLIENT_PROPERTIES.containsKey(SharedParams.CLIENT_DEFAULT_DATE_FORMAT)){
                    String dateFormat = String.valueOf(CLIENT_PROPERTIES.get(SharedParams.CLIENT_DEFAULT_DATE_FORMAT));
                    DATETIMEFORMAT = DateTimeFormat.getFormat(dateFormat);
                }
            }
            
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Unable to download client properties!");
            }
        });
    }
}
