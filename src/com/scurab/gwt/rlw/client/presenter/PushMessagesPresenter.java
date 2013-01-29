package com.scurab.gwt.rlw.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.RemoteLogWeb;
import com.scurab.gwt.rlw.client.view.PushMessageView;
import com.scurab.gwt.rlw.shared.model.Device;
import com.scurab.gwt.rlw.shared.model.GWTJsonHelper;
import com.scurab.gwt.rlw.shared.model.PushMessage;
import com.scurab.gwt.rlw.shared.model.PushMessageRequest;
import com.scurab.gwt.rlw.shared.model.PushMessageResponse;

public class PushMessagesPresenter extends TabBasePresenter {

    private DataServiceAsync mDataService;
    private HandlerManager mEventBus;
    private String mApp;
    private HTMLPanel mContainer;
    private PushMessageView mDisplay;
    
    private Device mDevice;
    
    public PushMessagesPresenter(DataServiceAsync dataService, HandlerManager eventBus, String appName,
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
            for(int i = 0, n = RemoteLogWeb.PUSH_MESSAGES.length;i<n;i++){
                PushMessage pm = RemoteLogWeb.PUSH_MESSAGES[i];
                if(!pm.isPlatformSupported(platform)){
                    continue;
                }
                if(pm.isOnlyForApp() && mApp == null){
                    continue;
                }
                lb.addItem(pm.getName(), String.valueOf(i));
            }
        }
        
        onSelectMessage(null);//simulate null value selection
    }
    
    protected PushMessage getSelectedMessage(){
        ListBox lb = mDisplay.getMessageListBox();
        int index = lb.getSelectedIndex();//null values is first
        if(index > 0){
            String name = lb.getItemText(index);
            String value = lb.getValue(index);
            int arrIndex = Integer.parseInt(value);
            return RemoteLogWeb.PUSH_MESSAGES[arrIndex];            
        }
        return null;
    }
    
    protected void onSendClick() {
        String json = GWTJsonHelper.toJson(getMessageRequest()).toString();
        notifyStartDownloading(null);
        final long ts = System.currentTimeMillis();
        final Button btnSend = mDisplay.getSendButton(); 
        btnSend.setEnabled(false);
        btnSend.setText(RemoteLogWeb.WORDS.Sending());
        mDataService.sendMessage(getMessageRequest(), new AsyncCallback<PushMessageResponse>() {
            @Override
            public void onFailure(Throwable caught) {
                mDisplay.getSendButton().setEnabled(true);
                btnSend.setText(RemoteLogWeb.WORDS.Send());
                notifyStopDownloading();
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(final PushMessageResponse result) {
                //notifiaction via button, so if respond is too quick, ill wait for at least 500ms                
                
                final long now = System.currentTimeMillis();
                long diff = now - ts;
                if (diff < 1000) {
                    Timer t = new Timer() {
                        public void run() {
                            onPushRespond(result);
                        }
                    };
                    t.schedule((int) Math.max(500, 1000 - diff));
                } else {
                    onPushRespond(result);
                }
            }
        });
    }
    
    protected void onPushRespond(PushMessageResponse rmd){
        final Button btnSend = mDisplay.getSendButton(); 
        btnSend.setEnabled(true);
        btnSend.setText(RemoteLogWeb.WORDS.Send());
        notifyStopDownloading();
        if(rmd.getRequest().getDevicePlatform().equalsIgnoreCase("windowsphone")){
            String status = rmd.getStatus();
            //default OK response for winphone
            if(!"[200] Notification:Received Subscription:Active Device:Connected".equals(status)){
                Window.alert(rmd.getStatus());
            }
        }
    }
    
    private PushMessageRequest getMessageRequest(){
        PushMessage pm = getSelectedMessage();
        PushMessageRequest pmr = new PushMessageRequest();
        pmr.setDeviceID(mDevice.getDeviceID());
        pmr.setDevicePlatform(mDevice.getPlatform());
        pmr.setPushToken(mDevice.getPushID());
        pmr.setMessage(pm);
        if(pm.hasParams()){
            pmr.setMessageParams(mDisplay.getMessageParams().getText());
        }
        pmr.setMessageContext(mDisplay.getContext().getValue());
        pmr.setWaitForRespond(mDisplay.getWaitCheckBox().getValue());
        pmr.setAppName(mApp);
        return pmr;
    }
    
    protected void onSelectMessage(PushMessage m){
        mDisplay.getSendButton().setEnabled(m != null);
        if(m != null){
            TextArea ta = mDisplay.getMessageParams();
            boolean hasParams = m.hasParams();
            ta.setEnabled(hasParams);
            ta.setText(m.getParamExample());
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
        
        String pushId = device != null ? device.getPushID() : null;
        initMessages(pushId != null ? device : null);
        mDisplay.getSendButton().setVisible(pushId != null ? true : false);
        Label errMsg = mDisplay.getErrorMessage();
        errMsg.setVisible(pushId == null ? true : false);
        if(pushId == null){
            errMsg.setText(RemoteLogWeb.WORDS.MissingPushID());
        }
        mDisplay.setVisible(pushId != null && pushId.length() > 0);
    }
}
