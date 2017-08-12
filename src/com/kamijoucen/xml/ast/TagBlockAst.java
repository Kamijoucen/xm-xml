package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.ast.result.TextResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public class TagBlockAst extends BaseAstWrapper {

    private String tagName;
    private TagStartAst start;
    private TagEndAst end;

    public TagBlockAst(String tagName) {
        this.tagName = tagName;
    }

    public void setAttrs(List<BaseResult> attrs) {
        this.attrs.addAll(attrs);
    }

    public void putAttr(String key, String val, TokenLocation tokenLocation) {
        attrs.add(new AttrResult(key, val, tokenLocation));
    }

    public void addChild(BaseAst b) {
        body.add(b);
    }

    public String getTagName() {
        return tagName;
    }


    public List<BaseAst> getBody() {
        return body;
    }

    public void addText(TextResult text) {
        texts.add(text);
    }

    public TagStartAst getStart() {
        return start;
    }

    public void setStart(TagStartAst start) {
        this.start = start;
    }

    public TagEndAst getEnd() {
        return end;
    }

    public void setEnd(TagEndAst end) {
        this.end = end;
    }

    @Override
    public TokenLocation getTokenLocation() {
        return null;
    }

}
