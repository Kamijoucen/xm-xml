package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;

public class TagStartAst extends BaseAst {

    private String tagName;

    public TagStartAst(String tagName, TokenLocation tokenLocation) {
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
