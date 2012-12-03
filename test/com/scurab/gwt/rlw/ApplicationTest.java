package com.scurab.gwt.rlw;

import static org.junit.Assert.*;

import org.junit.Test;

import com.scurab.gwt.rlw.server.Application;
import com.scurab.gwt.rlw.server.Database;
import com.scurab.gwt.rlw.server.Queries;

public class ApplicationTest {

    private static boolean init = false;

    public ApplicationTest() {
        if (!init) {
            Application app = new Application();
            app.contextInitialized(null);
        }
    }

    @Test
    public void test() {
        assertNotNull(Queries.getQuery(Queries.QueryNames.CREATE_TABLES));
    }
}
