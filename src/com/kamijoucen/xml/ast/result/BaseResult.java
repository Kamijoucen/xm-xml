package com.kamijoucen.xml.ast.result;

import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public interface BaseResult {
    String val();

    TokenLocation getTokenLocation();
}
