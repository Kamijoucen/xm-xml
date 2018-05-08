package com.kamijoucen.xml.ast.result;

import com.kamijoucen.xml.token.TokenLocation;

public interface BaseResult {
    String val();

    TokenLocation getTokenLocation();
}
