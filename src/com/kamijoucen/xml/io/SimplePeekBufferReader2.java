package com.kamijoucen.xml.io;

import java.io.IOException;
import java.io.Reader;

public class SimplePeekBufferReader2 extends Reader {
    private static final char EOF = '\0';
    private Reader in;
    private char[] buffer;
    private int bufIndex = -1;
    private int bufferLength;

    public SimplePeekBufferReader2(Reader in) throws IOException {
        super(in);
        this.in = in;
    }

    public SimplePeekBufferReader2(Reader in, int bufferSize) throws IOException {
        super(in);
        this.in = in;

    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        // TODO: 2017/8/8
        throw new IllegalStateException();
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
            buffer = null;
        }
    }

    @Override
    public int read() throws IOException {
        int index = ++bufIndex;
        if (index >= bufferLength) {
            if (bufferLength == -1) {
                return EOF;
            }



        }
        return buffer[index];
    }

    public int peek() throws IOException {
        return -1;
    }

    public int peek(int i) throws IOException {
        return -1;
    }

    private void fill() throws IOException {

    }
}
