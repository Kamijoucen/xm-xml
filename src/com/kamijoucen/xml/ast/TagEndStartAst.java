package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;

public class TagEndStartAst extends BaseAst {

    private String tagName;

    public TagEndStartAst(String tagName, TokenLocation tokenLocation) {
        super(tokenLocation);
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
