package com.scurab.gwt.rlw.client;

import java.util.HashMap;

import com.google.android.gcm.server.Message;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.scurab.gwt.rlw.client.presenter.MainPresenter;
import com.scurab.gwt.rlw.client.view.MainWindow;
import com.scurab.gwt.rlw.language.Words;
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.model.PushMessage;

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
    
    public static DateTimeFormat DATETIMEFORMAT = DateTimeFormat.getFormat("yyyy-MM-dd kk:mm:ss");
    
    public static final HashMap<String, Object> CLIENT_PROPERTIES = new HashMap<String, Object>();
    
    public static String BROWSER;
    
    public static PushMessage[] PUSH_MESSAGES;
    
    private static boolean isIE = false;

    /**
     * This is the entry point method.
     */
    @Override
    public void onModuleLoad() {
        BROWSER = getUserAgent();
        isIE = BROWSER.toLowerCase().contains("msie");
        
        RootPanel.get().add(new MainPresenter(mDataService, mEventBus, new MainWindow()));
        
        //load client properties
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
                    Properties.PAGE_SIZE = ((Number)(CLIENT_PROPERTIES.get(SharedParams.CLIENT_PAGE_SIZE))).intValue();    
                }
                if(CLIENT_PROPERTIES.containsKey(SharedParams.CLIENT_DEFAULT_DATE_FORMAT)){
                    String dateFormat = String.valueOf(CLIENT_PROPERTIES.get(SharedParams.CLIENT_DEFAULT_DATE_FORMAT));
                    DATETIMEFORMAT = DateTimeFormat.getFormat(dateFormat);
                }
                if(CLIENT_PROPERTIES.containsKey(SharedParams.CLIENT_AUTO_REFRESH)){
                    Properties.AUTO_REFRESH = ((Number)(CLIENT_PROPERTIES.get(SharedParams.CLIENT_AUTO_REFRESH))).intValue();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                RootPanel.get().clear();
                RootPanel.get().add(new Label("Unable to download client properties! => Unable to open MainWindow"));
            }
        });
        
        //download push messages
        mDataService.getPushMessages(new AsyncCallback<PushMessage[]>() {
            @Override
            public void onSuccess(PushMessage[] result) {
                PUSH_MESSAGES = result;
            }
            
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Unable to download push messages definition!");
                PUSH_MESSAGES = new PushMessage[0];
            }
        });
    }
    
    public static native String getUserAgent() /*-{
    return navigator.userAgent.toLowerCase();
    }-*/;

    public static class Properties{
        public static int PAGE_SIZE = 50;
        public static int AUTO_REFRESH = 5;
    }
    
    public static boolean isIE(){
        return isIE;
    }
}
