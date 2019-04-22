package com.kamijoucen.xml.ast;


import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class TagBlockAst extends BaseNormalAstAdapter {

    private String tagName;
    private TagStartAst start;
    private TagEndAst end;

    public TagBlockAst(String tagName) {
        this.tagName = tagName;
    }

    public void setAttrs(List<AttrAst> attrs) {
        this.start.attrs.addAll(attrs);
    }

    public void putAttr(String key, String val, TokenLocation tokenLocation) {
        this.start.attrs.add(new AttrAst(key, val, tokenLocation));
    }

    public void addChild(String tagName, NormalAst ast) {
        super.addChild(tagName, ast);
    }

    public String getTagName() {
        return tagName;
    }

    public void addText(TextAst text) {
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
    public AttrAst attr(String str) {
        return this.start.attr(str);
    }

    @Override
    public List<AttrAst> attrs(String str) {
        return this.start.attrs;
    }

    @Override
    public String toFormatString() {
        return null;
    }

    @Override
    public String builder(TemplateBuilderVisitor visitor) {
        return null;
    }

}
