package com.scurab.gwt.rlw.shared.model;

public class SettingsRespond extends Respond<Settings[]> {
    public SettingsRespond(Settings[] data) {
        super(data);
        setCount(data.length);
    }
}
