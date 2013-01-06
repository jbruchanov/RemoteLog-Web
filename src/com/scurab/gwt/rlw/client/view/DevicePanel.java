package com.scurab.gwt.rlw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;

public class DevicePanel extends Composite{

    private static DevicePanelUiBinder uiBinder = GWT.create(DevicePanelUiBinder.class);
    @UiField HTMLPanel mDevicePanel;
    @UiField HTMLPanel mMessagesPanel;

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
}
