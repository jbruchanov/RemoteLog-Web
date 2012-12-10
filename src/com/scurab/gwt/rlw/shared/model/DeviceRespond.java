package com.scurab.gwt.rlw.shared.model;

public class DeviceRespond extends Respond<Device> {

    public DeviceRespond() {
        super();
    }

    public DeviceRespond(Device context) {
        super(context);
    }

    public DeviceRespond(int count) {
        super(count);
    }

    public DeviceRespond(String msg, Device context) {
        super(msg, context);
    }

    public DeviceRespond(String msg, int count) {
        super(msg, count);
    }

    public DeviceRespond(Throwable t) {
        super(t);
    }

}
