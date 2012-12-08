package com.scurab.gwt.rlw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ContentView extends Composite {

    private static ContentViewUiBinder uiBinder = GWT.create(ContentViewUiBinder.class);

    @UiField
    protected Button btnLoadData;
    @UiField
    protected HTMLPanel mDevicesPanel;

    interface ContentViewUiBinder extends UiBinder<Widget, ContentView> {
    }

    public ContentView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public ContentView(String firstName) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public Button getnLoadDataButton() {
        return btnLoadData;
    }

    public HTMLPanel getDevicesPanel() {
        return mDevicesPanel;
    }
}
