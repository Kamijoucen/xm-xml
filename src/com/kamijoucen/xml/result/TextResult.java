package com.kamijoucen.xml.result;

import com.kamijoucen.xml.token.TokenLocation;

public class TextResult extends BaseResult {

    private String str;

    public TextResult(String str, TokenLocation tokenLocation) {
        super(tokenLocation);
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
