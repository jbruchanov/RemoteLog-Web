package com.scurab.gwt.rlw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContentView extends Composite {

    private static ContentViewUiBinder uiBinder = GWT.create(ContentViewUiBinder.class);

    @UiField
    protected HTMLPanel mDevicesPanel;
    @UiField
    protected HTMLPanel mLogsPanel;
    @UiField
    protected HTMLPanel mDevicePanel;
    @UiField
    protected TabPanel mTabPanel;

    interface ContentViewUiBinder extends UiBinder<Widget, ContentView> {
    }

    public ContentView() {
        initWidget(uiBinder.createAndBindUi(this));
        mTabPanel.selectTab(0);
    }

    public HTMLPanel getDevicesPanel() {
        return mDevicesPanel;
    }

    public HTMLPanel getLogsPanel() {
        return mLogsPanel;
    }

    public HTMLPanel getDevicePanel() {
        return mDevicePanel;
    }
}
