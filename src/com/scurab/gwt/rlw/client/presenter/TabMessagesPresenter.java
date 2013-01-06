package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.RemoteLogWeb;
import com.scurab.gwt.rlw.client.view.PushMessageView;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.PushMessage;

public class TabMessagesPresenter extends TabBasePresenter {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private String mApp;
    private HTMLPanel mContainer;
    private PushMessageView mDisplay;
    
    private Device mDevice;
    
    public TabMessagesPresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName,
            HTMLPanel tabPanel) {
        super(dataService, eventBus, appName, tabPanel);
        
        mDataService = dataService;
        mEventBus = eventBus;
        mApp = appName;
        mContainer = tabPanel;
        
        init();
        bind();
        
        onSelectMessage(null);//simulate null value selection
    }
    
    private void init(){
        mDisplay = onCreateView();
        mContainer.add(mDisplay);
    }
    
    private void bind(){
        mDisplay.getSendButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onSendClick();
            }
        });
        
        ListBox lb = mDisplay.getMessageListBox();
        lb.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                onSelectMessage(getSelectedMessage());
            }
        });
        
        
    }
    
    private void initMessages(Device d){
        ListBox lb = mDisplay.getMessageListBox();
        lb.clear();
        
        if(d != null){
            String platform = d.getPlatform();
            
            lb.addItem("","");
            for(PushMessage pm : RemoteLogWeb.PUSH_MESSAGES){
                if(pm.isPlatformSupported(platform)){
                    lb.addItem(pm.getName());
                }
            }
        }
        
        onSelectMessage(null);//simulate null value selection
    }
    
    protected PushMessage getSelectedMessage(){
        ListBox lb = mDisplay.getMessageListBox();
        int index = lb.getSelectedIndex()-1;//null values is first
        PushMessage m = null;
        if(index > -1){
            m = RemoteLogWeb.PUSH_MESSAGES[index];
        }
        return m;
    }
    
    protected void onSendClick() {
        PushMessage pm = getSelectedMessage();
        Window.alert(pm != null ? pm.getName() : "null");
    }
    
    protected void onSelectMessage(PushMessage m){
        mDisplay.getSendButton().setEnabled(m != null);
        if(m != null){
            TextArea ta = mDisplay.getMessageParams();
            boolean hasParams = m.hasParams();
            ta.setEnabled(hasParams);
            if(hasParams){
                ta.setText(m.getParamExample());
            }
        }
    }

    public PushMessageView onCreateView(){
        return new PushMessageView();
    }

    public Device getDevice() {
        return mDevice;
    }

    public void setDevice(Device device) {
        mDevice = device;
        String pushId = device.getPushID();
        initMessages(pushId != null ? device : null);
        mDisplay.getSendButton().setVisible(pushId != null ? true : false);
        Label errMsg = mDisplay.getErrorMessage();
        errMsg.setVisible(pushId == null ? true : false);
        if(pushId == null){
            errMsg.setText(RemoteLogWeb.WORDS.MissingPushID());
        }
    }
}
