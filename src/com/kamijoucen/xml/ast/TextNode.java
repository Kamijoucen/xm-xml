package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.build.TemplateBuilderVisitor;

public class TextNode implements BaseNode {

    protected String text;
    protected TokenLocation tokenLocation;

    public TextNode(String text, TokenLocation tokenLocation) {
        this.text = text;
        this.tokenLocation = tokenLocation;
    }

    public TextNode(String text) {
        this.text = text;
    }

    public String val() {
        return text;
    }

    @Override
    public String toFormatString() {
        return null;
    }

    @Override
    public String builder(TemplateBuilderVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    public String getText() {
        return text;
    }
}
