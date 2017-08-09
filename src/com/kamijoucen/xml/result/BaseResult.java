package com.kamijoucen.xml.result;

import com.kamijoucen.xml.token.TokenLocation;

public abstract class BaseResult {
    private TokenLocation tokenLocation;

    public BaseResult(TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
    }

    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    public void setTokenLocation(TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
    }
}
