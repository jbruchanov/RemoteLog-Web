package com.scurab.gwt.rlw.client.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.scurab.gwt.rlw.client.DataServiceAsync;
import com.scurab.gwt.rlw.client.components.DynamicTableWidget;
import com.scurab.gwt.rlw.client.view.ContentView;
import com.scurab.gwt.rlw.client.view.MainWindow;
import com.scurab.gwt.rlw.shared.model.Device;

public class MainPresenter extends BasePresenter implements IsWidget {

    private MainWindow mWindow;
    private ContentView mContentView;
    
    public MainPresenter(DataServiceAsync dataService, HandlerManager eventBus, MainWindow display) {
	super(dataService, eventBus, display);
	mWindow = display;
	init();
    }

    private void init(){
	mContentView = new ContentView();
	mWindow.getContentPanel().add(mContentView);
	
	mContentView.getnLoadDataButton().addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(ClickEvent event) {
		onLoadClick();
	    }
	});
    }
    
    private void onLoadClick(){
	mDataService.getDevices(new AsyncCallback<List<Device>>() {
	    
	    @Override
	    public void onSuccess(List<Device> result) {
		onLoadData(result);
	    }
	    
	    @Override
	    public void onFailure(Throwable caught) {		
		Window.alert(caught.getMessage());
	    }
	});
    }

    protected void onLoadData(List<Device> result) {
	List<HashMap<String, Object>> transformed = transform(result);
	DynamicTableWidget dtw = new DynamicTableWidget();
	dtw.setData(transformed);
	mContentView.getDevicesPanel().add(dtw);
    }
    
    private List<HashMap<String, Object>> transform(List<Device> data){
	List<HashMap<String, Object>> rCollection = new ArrayList<HashMap<String,Object>>();
	for(int i = 0;i<data.size();i++){
	    Device d = data.get(i);
	    HashMap<String, Object> result = new HashMap<String, Object>();
	    result.put("ID", d.getId());
	    result.put("Description", d.getDescription());
	    rCollection.add(result);
	}
	return rCollection;
    }
}
