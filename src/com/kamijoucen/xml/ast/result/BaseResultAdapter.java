package com.kamijoucen.xml.ast.result;

import com.kamijoucen.xml.token.TokenLocation;

public abstract class BaseResultAdapter implements BaseResult {
    protected TokenLocation tokenLocation;

    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }
}
