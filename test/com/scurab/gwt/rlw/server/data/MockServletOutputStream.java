package com.scurab.gwt.rlw.server.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class MockServletOutputStream extends ServletOutputStream {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public void write(int b) throws IOException {
        baos.write(b);
    }

    public String getStringValue() {
        return new String(baos.toByteArray());
    }
}