package com.kamijoucen.xml.io;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class SimplePeekBufferReader2 extends Reader {
    private static final char EOF = '\0';
    private Reader in;
    private char[] buffer;
    private int allBufferSize = 9999;
    private int bufIndex;
    private int bufferLength;

    public SimplePeekBufferReader2(Reader in) throws IOException {
        super(in);
        this.in = in;
        buffer = new char[allBufferSize];
        bufIndex = -1;
        initFill();
    }


    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        throw new IllegalStateException("不支持的调用");
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
            fill();
            if (bufferLength == -1) {
                return EOF;
            }
            index = ++bufIndex;
            return buffer[index];
        } else {
            return buffer[index];
        }
    }

    public int peek() throws IOException {
        return peek(1);
    }

    public int peek(int i) throws IOException {
        if (i <= 0 || i >= allBufferSize) {
            throw new IllegalArgumentException("参数长度错误:i=" + i);
        }
        // 这时的index是当前应该读取的
        int index = bufIndex + 1;
        if (index + i - 1 >= bufferLength) {
            fill();
        }
        if (bufferLength == -1) {
            return EOF;
        }
        if (i - 1 >= bufferLength) {
            return EOF;
        }
        index = bufIndex + 1;
        return buffer[index + i - 1];
    }


    private void fill() throws IOException {
        int size = bufferLength - 1 - bufIndex;
        if (size > 0) {
            System.arraycopy(buffer, bufIndex + 1, buffer, 0, size);
            bufferLength = in.read(buffer, size, allBufferSize - size) + size;
            bufIndex = -1;
        } else {
            bufferLength = in.read(buffer);
            bufIndex = -1;
        }
    }

    private void initFill() throws IOException {
        bufferLength = in.read(buffer);
        bufIndex = -1;
    }
}
