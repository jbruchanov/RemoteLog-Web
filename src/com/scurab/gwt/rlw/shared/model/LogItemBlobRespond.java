package com.scurab.gwt.rlw.shared.model;

public class LogItemBlobRespond extends Respond<LogItemBlobRequest> {

    public LogItemBlobRespond() {
        super();
    }

    public LogItemBlobRespond(int count) {
        super(count);
    }

    public LogItemBlobRespond(String msg, int count) {
        super(msg, count);
    }

    public LogItemBlobRespond(String msg, LogItemBlobRequest context) {
        super(msg, context);
    }

    public LogItemBlobRespond(Throwable t) {
        super(t);
    }

    public LogItemBlobRespond(LogItemBlobRequest context) {
        super(context);
    }
}
