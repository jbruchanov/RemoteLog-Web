package com.scurab.gwt.rlw.client.components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.scurab.gwt.rlw.client.RemoteLogWeb;
import com.scurab.gwt.rlw.shared.SharedParams;
import com.scurab.gwt.rlw.shared.TableColumns;
import com.scurab.gwt.rlw.shared.model.Device;

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
            if (value.startsWith("{") || value.startsWith("[")) {
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
    
    @Override
    protected void onFinishCreatingColumns(List<HashMap<String, Object>> mData,
            CellTable<HashMap<String, Object>> cellTable, ListHandler<HashMap<String, Object>> sortHandler) {
        super.onFinishCreatingColumns(mData, cellTable, sortHandler);

      //password column
        Column<HashMap<String,Object>, HashMap<String,Object>>column = new Column<HashMap<String,Object>, HashMap<String,Object>>(getActionCell()){
            @Override
            public HashMap<String, Object> getValue(HashMap<String, Object> object) {
                return object;
            }
        };
        cellTable.addColumn(column);
    }
    
    private ActionCell<HashMap<String,Object>> getActionCell(){
        ActionCell<HashMap<String,Object>> ac = new ActionCell<HashMap<String,Object>>(RemoteLogWeb.WORDS.Detail(), new Delegate<HashMap<String,Object>>() {
            @Override
            public void execute(HashMap<String,Object> object) {           
                Window.alert("" + object.get(TableColumns.DeviceID));
            }
        });
        return ac;
    }
}
