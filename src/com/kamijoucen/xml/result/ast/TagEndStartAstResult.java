package com.kamijoucen.xml.result.ast;

import com.kamijoucen.xml.result.BaseResult;
import com.kamijoucen.xml.token.TokenLocation;

public class TagEndStartAstResult extends BaseResult {

    private String tagName;

    public TagEndStartAstResult(String tagName, TokenLocation tokenLocation) {
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
