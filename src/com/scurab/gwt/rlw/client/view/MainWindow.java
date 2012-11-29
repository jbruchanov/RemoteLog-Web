package com.scurab.gwt.rlw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;

public class MainWindow extends BaseWindow{

    private static MainWindowUiBinder uiBinder = GWT
	    .create(MainWindowUiBinder.class);
    
    @UiField
    protected HTMLPanel contentPanel;

    interface MainWindowUiBinder extends UiBinder<Widget, MainWindow> {
    }

    public MainWindow() {
	initWidget(uiBinder.createAndBindUi(this));
    }

    public MainWindow(String firstName) {
	initWidget(uiBinder.createAndBindUi(this));
    }

    public HTMLPanel getContentPanel() {
	return contentPanel;
    }
}
