package com.scurab.gwt.rlw.client.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.scurab.gwt.rlw.client.RemoteLogWeb;

public class QuestionDialog extends DialogBox {
    private static final int WIDTH = 300;
    private static final int PIC_WIDTH = 64;

    public interface OnQuestionDialogButtonClick {
        void onYesButtonClick();

        void onNoButtonClick();

        void onCancelButtonClick();
    }

    private Button yesButton = null;
    private Button noButton = null;
    private Button cancelButton = null;

    public enum Type {
        YesNo, YesNoCancel
    }

    public QuestionDialog(String message, OnQuestionDialogButtonClick listener) {
        this(message, Type.YesNo, listener);
    }

    public QuestionDialog(String message, Type type, OnQuestionDialogButtonClick listener) {
        setAutoHideEnabled(false);
        setGlassEnabled(true);
        center();
        build(message, type, listener);
    }

    private void build(String message, Type type, final OnQuestionDialogButtonClick listener) {
        FlexTable flextable = new FlexTable();
        flextable.setWidth(WIDTH + "px");
        flextable.setHeight("150px");
        int row = flextable.getRowCount();
        flextable.setWidget(row, 0, new Image(/* AppConstants.BigIcons.ICO_QUESTION */));
        Label l = new Label(message);
        l.setStyleName("dialog-informationText");
        flextable.setWidget(row, 1, l);
        flextable.getFlexCellFormatter().setWidth(0, 0, PIC_WIDTH + "px");
        flextable.getFlexCellFormatter().setWidth(0, 1, (WIDTH - PIC_WIDTH) + "px");

        buildButtons(flextable, type, listener);
        this.add(flextable);
    }

    private void buildButtons(final FlexTable flextable, Type type, final OnQuestionDialogButtonClick listener) {
        flextable.getFlexCellFormatter().setStyleName(1, 0, "center");

        HorizontalPanel hp = new HorizontalPanel();
        hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        hp.setStyleName("center");
        if (type == Type.YesNoCancel) {
            flextable.getFlexCellFormatter().setColSpan(1, 0, 2);
            hp.setWidth("100%");
        } else {
            flextable.getCellFormatter().setWidth(1, 0, "100px");
            hp.setWidth("66%");
        }

        yesButton = new Button(RemoteLogWeb.WORDS.Yes());

        yesButton.setStyleName("dialog-okbutton gwt-Button");
        yesButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                QuestionDialog.this.hide();
                listener.onYesButtonClick();
            }
        });

        hp.add(yesButton);

        noButton = new Button(RemoteLogWeb.WORDS.No());
        noButton.setStyleName("dialog-okbutton gwt-Button");
        noButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                QuestionDialog.this.hide();
                listener.onNoButtonClick();
            }
        });
        hp.add(noButton);

        if (type == Type.YesNoCancel) {
            cancelButton = new Button(RemoteLogWeb.WORDS.Cancel());
            cancelButton.setStyleName("dialog-okbutton gwt-Button");
            cancelButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    QuestionDialog.this.hide();
                    listener.onCancelButtonClick();
                }
            });
            hp.add(cancelButton);
        }

        if (type == Type.YesNoCancel) {
            flextable.setWidget(1, 0, hp);
        } else {
            flextable.setWidget(1, 1, hp);
        }
    }

    public static void show(OnQuestionDialogButtonClick listener) {
        show(RemoteLogWeb.WORDS.ReallyQstn(), Type.YesNo, listener);
    }

    public static void show(String msg, OnQuestionDialogButtonClick listener) {
        show(msg, Type.YesNo, listener);
    }

    public static void show(String msg, Type type, OnQuestionDialogButtonClick listener) {
        QuestionDialog qd = new QuestionDialog(msg, type, listener);
        qd.show();
    }
}
