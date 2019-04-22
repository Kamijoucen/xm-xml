package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class SingleTagStartNode extends TagStartNode {

    @Override
    public TagStartType startType() {
        return TagStartType.SINGLE;
    }

    public SingleTagStartNode(String tagName, List<AttrNode> attrs, TagStartType type, TokenLocation tokenLocation) {
        super(tagName, attrs, type, tokenLocation);
    }

    @Override
    public String toFormatString() {
        return super.toFormatString();
    }

    @Override
    public String builder(TemplateBuilderVisitor visitor) {
        return super.builder(visitor);
    }
}
