package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;

public class BaseAst {
    private TokenLocation tokenLocation;

    public BaseAst(TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
    }
}
