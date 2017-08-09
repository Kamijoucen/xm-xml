package com.kamijoucen.xml.result.ast;

import com.kamijoucen.xml.result.BaseResult;
import com.kamijoucen.xml.token.TokenLocation;

public class ChildTextAstResult extends BaseResult {
    private String text;
    public ChildTextAstResult(String text, TokenLocation tokenLocation) {
        super(tokenLocation);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
