package com.kamijoucen.xml.ast;


import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class TagBlockNode extends BaseTagNodeAdapter {

    private TagStartType type;

//    private TagEndNode end;

    public TagBlockNode(String tagName, List<AttrNode> attrs, TagBlockNode.TagStartType type, TokenLocation tokenLocation) {
        this.type = type;
        super.tagName = tagName;
        super.attrs.addAll(attrs);
        super.tokenLocation = tokenLocation;
    }

    public TagBlockNode(String tagName) {
        this.tagName = tagName;
    }

    public TagBlockNode putAttrs(List<AttrNode> attrs) {
        super.attrs.addAll(attrs);
        return this;
    }

    public void putAttr(String key, String val, TokenLocation tokenLocation) {
        super.attrs.add(new AttrNode(key, val, tokenLocation));
    }

    public TagBlockNode putAttr(String key, String val) {
        this.putAttr(key, val, null);
        return this;
    }

    public TagBlockNode putAttr(AttrNode node) {
        super.attrs.add(node);
        return this;
    }

    @Override
    public void addChildText(TextNode text) {
        texts.add(text);
    }

    @Override
    public AttrNode attr(String str) {
        return super.attr(str);
    }

    @Override
    public List<AttrNode> attrs(String str) {
        return super.attrs;
    }

    @Override
    public String toFormatString() {
        return null;
    }

    @Override
    public String builder(TemplateBuilderVisitor visitor) {
        return visitor.visit(this);
    }

    public TagStartType getType() {
        return type;
    }

    public void setType(TagStartType type) {
        this.type = type;
    }

    public enum TagStartType {
        BLOCK,
        SINGLE
    }
}
