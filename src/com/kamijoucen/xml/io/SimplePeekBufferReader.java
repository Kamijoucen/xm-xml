package com.kamijoucen.xml.io;

import java.io.IOException;
import java.io.Reader;

public class SimplePeekBufferReader extends Reader {
    private Reader in;
    private char[] cb;
    private int catchSize = 0;
    private int charIndex = -1;
    private int catchLength = 9999;

    public SimplePeekBufferReader(Reader in) throws IOException {
        super(in);
        this.in = in;
        cb = new char[catchLength];
        fill();
    }

    public SimplePeekBufferReader(Reader in, int bufferSize) throws IOException {
        super(in);
        this.in = in;
        cb = new char[bufferSize];
        fill();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        // TODO: 2017/8/8
        return -1;
    }

    @Override
    public void close() throws IOException {
        if (in == null) {
            return;
        }
        try {
            in.close();
        } finally {
            in = null;
            cb = null;
        }
    }

    @Override
    public int read() throws IOException {
        if (charIndex + 1 > catchSize) {
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
        if (charIndex + 1 > catchSize) {
            fill();
            if (charIndex > catchSize) {
                return -1;
            } else {
                return cb[charIndex];
            }
        } else {
            return cb[charIndex];
        }
    }

    public int peek(int i) throws IOException {
        if (i > catchLength) {
            return -1;
        }
        if (catchSize != catchLength) {
            if (charIndex + i > catchSize) {
                return -1;
            }
            return cb[charIndex + i - 1];
        }
        if (charIndex + i > catchSize) {
            fill();
            if (charIndex > catchSize) {
                return -1;
            } else {
                return cb[charIndex + i - 1];
            }
        } else {
            return cb[charIndex + i - 1];
        }
    }

    private void fill() throws IOException {
        if (catchSize == 0) {
            catchSize = in.read(cb);
        } else {
            int size = catchSize - charIndex;
            System.arraycopy(cb, charIndex, cb, 0, size);
            catchSize = in.read(cb, size, catchLength - size) + size;
        }
        charIndex = 0;
    }
}
