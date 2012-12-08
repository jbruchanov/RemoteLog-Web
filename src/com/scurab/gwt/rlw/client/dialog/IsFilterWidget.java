package com.scurab.gwt.rlw.client.dialog;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IsWidget;

public interface IsFilterWidget extends IsWidget {
    Button getOkButton();

    Button getCancelButton();
    
    void refreshData();
}
