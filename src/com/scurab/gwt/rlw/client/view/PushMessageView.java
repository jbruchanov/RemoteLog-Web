package com.scurab.gwt.rlw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class PushMessageView extends Composite {

    private static PushMessageViewUiBinder uiBinder = GWT.create(PushMessageViewUiBinder.class);
    @UiField Button mSendButton;
    @UiField CheckBox mWaitCheckBox;
    @UiField ListBox mMessageListBox;
    @UiField TextArea mMessageParams;
    @UiField Label mErrorMessage;
    @UiField TextBox mContext;

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

    public Label getErrorMessage() {
        return mErrorMessage;
    }

    public TextBox getContext() {
        return mContext;
    }
}
