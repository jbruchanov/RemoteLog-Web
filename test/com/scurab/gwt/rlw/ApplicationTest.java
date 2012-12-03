package com.scurab.gwt.rlw;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.Database;

public class ApplicationTest {

    private static boolean init = false;

    public ApplicationTest() {
        if (!init) {
            Application app = new Application();
            app.contextInitialized(null);
        }
    }
}
