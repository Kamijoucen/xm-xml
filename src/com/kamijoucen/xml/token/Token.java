package com.kamijoucen.xml.token;

/**
 * 词法单元
 */
public class Token {
    private TokenLocation tokenLocation;
    private TokenType tokenType;
    private String strVal;

    public Token(TokenLocation tokenLocation, TokenType tokenType, String strVal) {
        this.tokenLocation = tokenLocation;
        this.tokenType = tokenType;
        this.strVal = strVal;
    }

    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getStrVal() {
        return strVal;
    }


}
