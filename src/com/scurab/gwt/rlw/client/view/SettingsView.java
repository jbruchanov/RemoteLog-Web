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
import com.google.gwt.user.client.ui.TextArea;

public class SettingsView extends Composite{

    private static SettingsViewUiBinder uiBinder = GWT.create(SettingsViewUiBinder.class);
    @UiField Button mDeleteDeviceSettings;
    @UiField Button mSave;
    @UiField TextArea mTextArea;

    interface SettingsViewUiBinder extends UiBinder<Widget, SettingsView> {
    }

    public SettingsView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public Button getDeleteDeviceSettings() {
        return mDeleteDeviceSettings;
    }

    public Button getSave() {
        return mSave;
    }

    public TextArea getTextArea() {
        return mTextArea;
    }
}
