package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

public class TextAst extends BaseAst {

    protected String text;
    protected TokenLocation tokenLocation;

    public TextAst(String text, TokenLocation tokenLocation) {
        this.text = text;
        this.tokenLocation = tokenLocation;
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
        return null;
    }

    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    public String getText() {
        return text;
    }
}
