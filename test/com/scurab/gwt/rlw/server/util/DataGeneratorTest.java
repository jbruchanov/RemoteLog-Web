package com.scurab.gwt.rlw.server.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DataGeneratorTest {

    @Test
    public void testGenRandomString() {
        for (int i = 0; i < 100; i++) {
            String s = DataGenerator.genRandomString();
            System.out.println(s);
        }
    }

    @Test
    public void testGenRandomStringIntInt() {
        for (int i = 0; i < 100; i++) {
            String s = DataGenerator.genRandomString(10, 15);
            assertTrue(s.length() >= 10 && s.length() <= 15);
            System.out.println(s);
        }
    }
}
