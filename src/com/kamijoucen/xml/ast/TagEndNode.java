package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class TagEndNode extends BaseTagNodeAdapter {

    public TagEndNode(String tagName, TokenLocation tokenLocation) {
        super.tokenLocation = tokenLocation;
        super.tagName = tagName;
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
