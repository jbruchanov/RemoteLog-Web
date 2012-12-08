package com.scurab.gwt.rlw.client.components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;

public class DeviceTableWidget extends DynamicTableWidget {

    public DeviceTableWidget() {
        super();
    }

    public DeviceTableWidget(List<HashMap<String, Object>> data) {
        super(data);
    }

    @Override
    protected Cell<String> getCell(String key, Object dataExample) {
        return new CustomTextCell();
    }

    public static native void createWindow(String text) /*-{
		var win = window.open("", "win", "width=300,height=200"); // a window object
		win.document.open("", "replace");
		win.document.write(text);
		win.document.close();
    }-*/;

    public class CustomTextCell extends TextCell {
        HashSet<String> mEvents = new HashSet<String>();

        @Override
        public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, String value,
                NativeEvent event, ValueUpdater<String> valueUpdater) {
            super.onBrowserEvent(context, parent, value, event, valueUpdater);
            if(value.startsWith("{") || value.startsWith("[")){
                createWindow(value);
            }
        }

        @Override
        public Set<String> getConsumedEvents() {
            if (mEvents.size() == 0) {
                mEvents.add("dblclick");
            }
            return mEvents;
        }

    }

}
