package com.kamijoucen.xml.element;


import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.build.BuilderVisitor;

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

    public TagBlockNode(String tagName, TagStartType type) {
        this.type = type;
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
    public String formatBuilder() {
        return null;
    }

    @Override
    public String builder(BuilderVisitor visitor) {
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
