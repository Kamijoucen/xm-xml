package com.kamijoucen.xml.element;

import com.kamijoucen.xml.build.FormatBuilderVisitor;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.build.BuilderVisitor;

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
    public String formatBuilder(FormatBuilderVisitor visitor) {
        visitor.addDepth();
        String str = visitor.visit(this);
        visitor.subDepth();
        return str;
    }

    @Override
    public String builder(BuilderVisitor visitor) {
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
