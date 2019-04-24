package com.kamijoucen.xml.ast;

import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

public class AttrNode extends BaseNode {

    private String key;
    private String val;
    protected TokenLocation tokenLocation;

    public AttrNode(String key, String val, TokenLocation tokenLocation) {
        Validate.notBlankVal(key);
        Validate.notNull(tokenLocation);
        this.key = key;
        this.val = val;
        this.tokenLocation = tokenLocation;
    }

    protected AttrNode(String key, String val) {
        Validate.notBlankVal(key);
        this.key = key;
        this.val = val;
    }

    protected AttrNode(TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
    }

    @Override
    TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    @Override
    String toFormatString() {
        return null;
    }

    @Override
    String builder(TemplateBuilderVisitor visitor) {
        return visitor.visit(this);
    }

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }

}
