package com.kamijoucen.xml.ast;


import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class TagBlockNode extends BaseTagNodeAdapter {

    private String tagName;
    private TagStartNode start;
    private TagEndNode end;

    public TagBlockNode(String tagName) {
        this.tagName = tagName;
    }

    public void setAttrs(List<AttrNode> attrs) {
        this.start.attrs.addAll(attrs);
    }

    public void putAttr(String key, String val, TokenLocation tokenLocation) {
        this.start.attrs.add(new AttrNode(key, val, tokenLocation));
    }

    @Override
    public void addChild(String tagName, TagNode ast) {
        super.addChild(tagName, ast);
    }

    public String getTagName() {
        return tagName;
    }

    public void addText(TextNode text) {
        texts.add(text);
    }

    public TagStartNode getStart() {
        return start;
    }

    public void setStart(TagStartNode start) {
        this.start = start;
    }

    public TagEndNode getEnd() {
        return end;
    }

    public void setEnd(TagEndNode end) {
        this.end = end;
    }

    @Override
    public AttrNode attr(String str) {
        return this.start.attr(str);
    }

    @Override
    public List<AttrNode> attrs(String str) {
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
