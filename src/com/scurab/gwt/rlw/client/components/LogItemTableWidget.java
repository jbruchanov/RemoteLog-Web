package com.scurab.gwt.rlw.client.components;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.Window;
import com.scurab.gwt.rlw.client.RemoteLogWeb;
import com.scurab.gwt.rlw.shared.TableColumns;

public class LogItemTableWidget extends DynamicTableWidget {
    public LogItemTableWidget() {
        super();
    }

    public LogItemTableWidget(List<HashMap<String, Object>> data) {
        super(data);
    }

    @Override
    protected void onFinishCreatingColumns(List<HashMap<String, Object>> mData,
            CellTable<HashMap<String, Object>> cellTable, ListHandler<HashMap<String, Object>> sortHandler) {
        super.onFinishCreatingColumns(mData, cellTable, sortHandler);

        // password column
        Column<HashMap<String, Object>, HashMap<String, Object>> column = new Column<HashMap<String, Object>, HashMap<String, Object>>(
                getActionCell()) {
            @Override
            public HashMap<String, Object> getValue(HashMap<String, Object> object) {
                return object;
            }

            @Override
            public void render(Context context, HashMap<String, Object> object, SafeHtmlBuilder sb) {
                Object o = object.get(TableColumns.LogBlobMime);
                if (o != null && o.toString().length() > 0) {
                    super.render(context, object, sb);
                }
            }
        };
        cellTable.addColumn(column);
    }

    private ActionCell<HashMap<String, Object>> getActionCell() {
        ActionCell<HashMap<String, Object>> ac = new ActionCell<HashMap<String, Object>>(RemoteLogWeb.WORDS.Download(),
                new Delegate<HashMap<String, Object>>() {
            @Override
            public void execute(HashMap<String, Object> object) {
                String url = "blobs/" + object.get(TableColumns.LogItemID);
                if(RemoteLogWeb.isIE()){
                    url = "/" + url;
                }
                Window.open(url  , "_blank", null);
            }
        });
        return ac;
    }
}
