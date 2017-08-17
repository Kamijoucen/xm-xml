package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public class TagEndAst implements BaseAst {

    private String tagName;
    private TokenLocation tokenLocation;

    public TagEndAst(String tagName, TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    @Override
    public BaseAst child(String s) {
        return null;
    }

    @Override
    public List<BaseAst> childs(String s) {
        return null;
    }

    @Override
    public BaseResult attr(String s) {
        return null;
    }

    @Override
    public List<BaseAst> attrs(String s) {
        return null;
    }

    @Override
    public String firstChildText() {
        return null;
    }

    @Override
    public List<String> childtexts() {
        return null;
    }
}
