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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;

public class PushMessageView extends Composite {

    private static PushMessageViewUiBinder uiBinder = GWT.create(PushMessageViewUiBinder.class);
    @UiField Button mSendButton;
    @UiField CheckBox mWaitCheckBox;
    @UiField ListBox mMessageListBox;
    @UiField TextArea mMessageParams;

    interface PushMessageViewUiBinder extends UiBinder<Widget, PushMessageView> {
    }

    public PushMessageView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public Button getSendButton() {
        return mSendButton;
    }

    public CheckBox getWaitCheckBox() {
        return mWaitCheckBox;
    }

    public ListBox getMessageListBox() {
        return mMessageListBox;
    }

    public TextArea getMessageParams() {
        return mMessageParams;
    }
}