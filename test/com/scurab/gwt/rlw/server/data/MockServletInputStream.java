package com.scurab.gwt.rlw.server.data;

import java.io.IOException;

import javax.servlet.ServletInputStream;

public class MockServletInputStream extends ServletInputStream {

    private byte[] mData;
    private int mIndex;

    public MockServletInputStream(byte[] data) {
        mData = data;
    }

    @Override
    public int read() throws IOException {
        if (mIndex >= mData.length) {
            return -1;
        }
        return mData[mIndex++];
    }

    @Override
    public synchronized void reset() throws IOException {
        mIndex = 0;
    }

    @Override
    public long skip(long n) throws IOException {
        mIndex += n;
        return mIndex;
    };
}