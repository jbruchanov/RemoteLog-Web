package com.scurab.gwt.rlw.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.scurab.gwt.rlw.client.RemoteLogWeb;

public class NotificationDialog extends DialogBox{
	public enum NotificationType
	{
		Information,
		Warning,
		Error
	}

	private static final int WIDTH = 500;
	private static final int PIC_WIDTH = 64;

	public NotificationDialog(String message, NotificationType type) {
		setAutoHideEnabled(false);
		setGlassEnabled(true);
		build(message, type);
		center();
	}

	private void build(String message, NotificationType type)
	{
		FlexTable flextable = new FlexTable();
		flextable.setWidth(WIDTH + "px");
		flextable.setHeight("150px");
		int row = flextable.getRowCount();
        flextable.setWidget(row, 0, new Image(/*getImageUrl(type)*/));
        HTML l = new HTML(message);
        l.setStyleName("dialog-informationText");
        flextable.setWidget(row, 1, l);
        flextable.getFlexCellFormatter().setWidth(0, 0, PIC_WIDTH  + "px");
        flextable.getFlexCellFormatter().setWidth(0, 1, (WIDTH - PIC_WIDTH) + "px");

		Button btn = new Button(RemoteLogWeb.WORDS.OK());
		btn.setStyleName("dialog-okbutton gwt-Button");
		flextable.getFlexCellFormatter().setColSpan(1, 0, 2);
		flextable.getFlexCellFormatter().setStyleName(1, 0, "dialog-okbutton");
		this.add(btn);
		btn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				NotificationDialog.this.hide();
			}
		});
		btn.setFocus(true);
		flextable.setWidget(1, 0, btn);
		this.add(flextable);
	}

//	private static final String getImageUrl(NotificationType type)
//	{
//		switch(type)
//		{
//			case Error:
//				return AppConstants.BigIcons.ICO_ERROR;
//			case Warning:
//				return AppConstants.BigIcons.ICO_ALERT;
//			case Information:
//			default:
//				return AppConstants.BigIcons.ICO_INFO;
//		}
//	}

	public static void showInfo(String message)
	{
		new NotificationDialog(message, NotificationType.Information).show();
	}
	public static void showError(String message)
	{
		if(message == null)
			message = "NullMessage (NullPointerException)";
		new NotificationDialog(message, NotificationType.Error).show();
	}

	public static void showError(Throwable caught)
	{
		showError(caught.getMessage());
	}

	public static void showWarning(String message)
	{
		new NotificationDialog(message, NotificationType.Warning).show();
	}
}
