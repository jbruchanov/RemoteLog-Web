package com.scurab.gwt.rlw;

import org.junit.Test;

import com.scurab.gwt.rlw.server.Application;

public class Database {

    @Test
    public void initHibernate() {
        Application app = new Application();
        app.contextInitialized(null);
    }
}
