package com.kamijoucen.xml.common;

import java.io.IOException;
import java.io.Reader;

public class SimpleBufferReader extends Reader {
    private Reader in;
    private char[] cb;
    private int catchSize = 0;
    private int charIndex = 0;

    public SimpleBufferReader(Reader in) {
        super(in);
        this.in = in;
        cb = new char[8888];
    }

    public SimpleBufferReader(Reader in, int bufferSize) {
        super(in);
        this.in = in;
        cb = new char[bufferSize];
    }

    public int read() throws IOException {
        if (charIndex >= catchSize) {
            fill();
            if (catchSize == -1) {
                return catchSize;
            } else {
                return cb[charIndex++];
            }
        } else {
            return cb[charIndex++];
        }
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        // TODO: 2017/8/8
        return -1;
    }

    @Override
    public void close() throws IOException {
        if (in == null)
            return;
        try {
            in.close();
        } finally {
            in = null;
            cb = null;
        }
    }

    public int peek() throws IOException {
        // FIXME: 2017/8/8
        if (charIndex >= catchSize) {
            fill();
            if (charIndex >= catchSize) {
                return -1;
            } else {
                return cb[charIndex];
            }
        } else {
            return cb[charIndex];
        }
    }

    private void fill() throws IOException {
        catchSize = in.read(cb);
        charIndex = 0;
    }
}
