package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;

public class ChildTextAst extends BaseAst {
    private String text;
    public ChildTextAst(String text, TokenLocation tokenLocation) {
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
