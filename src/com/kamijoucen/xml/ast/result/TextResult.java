package com.kamijoucen.xml.ast.result;

import com.kamijoucen.xml.token.TokenLocation;

public class TextResult implements BaseResult {

    private String str;

    public TextResult(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String val() {
        return str;
    }
}
