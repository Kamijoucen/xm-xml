package com.kamijoucen.xml.io;

import java.io.IOException;
import java.io.Reader;

public class SimpleBufferReader extends Reader {
    private Reader in;
    private char[] cb;
    private int catchSize = 0;
    private int charIndex = 0;
    private int catchLength = 8888;

    public SimpleBufferReader(Reader in) {
        super(in);
        this.in = in;
        cb = new char[catchLength];
    }

    public SimpleBufferReader(Reader in, int bufferSize) {
        super(in);
        this.in = in;
        cb = new char[bufferSize];
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

    public int read() throws IOException {
        if (charIndex + 1 >= catchSize) {
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

    public int peek() throws IOException {
        if (charIndex + 1 >= catchSize) {
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
        if (catchSize == 0) {
            catchSize = in.read(cb);
        } else {
            cb[0] = cb[catchSize - 1];
            catchSize = in.read(cb, 1, catchLength - 1) + 1;
            charIndex = 0;
        }
    }
}
