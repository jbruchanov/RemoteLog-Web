package com.scurab.gwt.rlw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainWindow extends BaseWindow{

    private static MainWindowUiBinder uiBinder = GWT
	    .create(MainWindowUiBinder.class);
    
    @UiField
    HTMLPanel contentPanel;
    @UiField
    StackLayoutPanel mMenuStack;
    @UiField
    HTMLPanel mMenuItems;

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

    public HTMLPanel getMenuItemsContainer() {
        return mMenuItems;
    }
}
