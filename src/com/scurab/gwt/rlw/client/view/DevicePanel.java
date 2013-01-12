package com.scurab.gwt.rlw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class DevicePanel extends Composite{

    private static DevicePanelUiBinder uiBinder = GWT.create(DevicePanelUiBinder.class);
    @UiField HTMLPanel mDevicePanel;
    @UiField HTMLPanel mMessagesPanel;
    @UiField HTMLPanel mSettingsPanel;

    interface DevicePanelUiBinder extends UiBinder<Widget, DevicePanel> {
    }

    public DevicePanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public HTMLPanel getDevicePanel() {
        return mDevicePanel;
    }

    public HTMLPanel getMessagesPanel() {
        return mMessagesPanel;
    }

    public HTMLPanel getSettingsPanel() {
        return mSettingsPanel;
    }
}
