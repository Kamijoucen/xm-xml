package com.kamijoucen.xml.core;

import com.kamijoucen.xml.token.Token;

import java.io.Closeable;

public interface Scanner extends Closeable {
    Token getToken();
    Token getNextToken();
}
