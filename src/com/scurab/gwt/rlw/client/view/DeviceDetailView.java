package com.scurab.gwt.rlw.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DeviceDetailView extends Composite{

    private static DeviceDetailViewUiBinder uiBinder = GWT.create(DeviceDetailViewUiBinder.class);

    interface DeviceDetailViewUiBinder extends UiBinder<Widget, DeviceDetailView> {
    }

    public DeviceDetailView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField Button mRawDetailButton;
    @UiField Label mID;
    @UiField Label mBrand;
    @UiField Label mDescription;
    @UiField Label mDetail;
    @UiField Label mDevUUID;
    @UiField Label mModel;
    @UiField Label mOSDescription;
    @UiField Label mOwner;
    @UiField Label mPlatform;
    @UiField Label mPushID;
    @UiField Label mResolution;
    @UiField Label mVersion;
    
    public Button getRawDetailButton() {
        return mRawDetailButton;
    }
    public Label getID() {
        return mID;
    }
    public Label getBrand() {
        return mBrand;
    }
    public Label getDescription() {
        return mDescription;
    }
    public Label getDetail() {
        return mDetail;
    }
    public Label getDevUUID() {
        return mDevUUID;
    }
    public Label getModel() {
        return mModel;
    }
    public Label getOSDescription() {
        return mOSDescription;
    }
    public Label getOwner() {
        return mOwner;
    }
    public Label getPlatform() {
        return mPlatform;
    }
    public Label getPushID() {
        return mPushID;
    }
    public Label getResolution() {
        return mResolution;
    }
    public Label getVersion() {
        return mVersion;
    }

}
