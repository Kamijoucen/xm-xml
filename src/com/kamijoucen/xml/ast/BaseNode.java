package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;

public interface BaseNode {

    String toFormatString();

    String builder();

    TokenLocation getTokenLocation();
}
