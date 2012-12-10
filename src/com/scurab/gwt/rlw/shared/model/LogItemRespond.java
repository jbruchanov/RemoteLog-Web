package com.scurab.gwt.rlw.shared.model;

public class LogItemRespond extends Respond<LogItem> {

    public LogItemRespond() {
        super();
    }

    public LogItemRespond(int count) {
        super(count);
    }

    public LogItemRespond(LogItem context) {
        super(context);
    }

    public LogItemRespond(String msg, int count) {
        super(msg, count);
    }

    public LogItemRespond(String msg, LogItem context) {
        super(msg, context);
    }

    public LogItemRespond(Throwable t) {
        super(t);
    }
}
