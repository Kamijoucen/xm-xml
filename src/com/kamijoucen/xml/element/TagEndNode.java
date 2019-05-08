package com.kamijoucen.xml.element;

import com.kamijoucen.xml.build.BuilderVisitor;
import com.kamijoucen.xml.build.FormatBuilderVisitor;
import com.kamijoucen.xml.build.Visitor;
import com.kamijoucen.xml.token.TokenLocation;

public class TagEndNode extends BaseTagNodeAdapter {

    public TagEndNode(String tagName, TokenLocation tokenLocation) {
        super.tokenLocation = tokenLocation;
        super.tagName = tagName;
    }

    @Override
    public String builder(Visitor visitor) {
        return null;
    }
}
