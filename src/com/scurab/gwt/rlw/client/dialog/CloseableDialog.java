package com.scurab.gwt.rlw.client.dialog;


import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

/**
 * @author amal
 * @version 1.0
 */
public class CloseableDialog extends DialogBox {


	Button close = new Button("X");
//	Image close = new Image(/**AppConstants.SmallIcons.ICO_ERROR**/);
	HTML title = new HTML("");
	HorizontalPanel captionPanel = new HorizontalPanel();

	public CloseableDialog(boolean autoHide, boolean modal) {
		super(autoHide, modal);
		Element td = getCellElement(0, 1);
		DOM.removeChild(td, (Element) td.getFirstChildElement());
		DOM.appendChild(td, captionPanel.getElement());
		captionPanel.setWidth("100%");
		captionPanel.setStyleName("Caption");// width-100%
		captionPanel.add(title);
		close.addStyleName("CloseButton gwt-Button");// float:right
		captionPanel.add(close);
		super.setGlassEnabled(true);
//		super.setAnimationEnabled(true);
		center();
	}

	public CloseableDialog(boolean autoHide) {
		this(autoHide, true);
	}

	public CloseableDialog() {
		this(false);
	}

	@Override
	public String getHTML() {
		return this.title.getHTML();
	}

	@Override
	public String getText() {
		return this.title.getText();
	}

	@Override
	public void setHTML(String html) {
		this.title.setHTML(html);
	}

	@Override
	public void setText(String text) {
		this.title.setText(text);
	}

	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		NativeEvent nativeEvent = event.getNativeEvent();

		if (!event.isCanceled() && (event.getTypeInt() == Event.ONCLICK)
				&& isCloseEvent(nativeEvent)) {
			this.hide();
		}
		super.onPreviewNativeEvent(event);
	}

	private boolean isCloseEvent(NativeEvent event) {
		return event.getEventTarget().equals(close.getElement());// compares
																	// equality
																	// of the
																	// underlying
																	// DOM
																	// elements
	}
}
