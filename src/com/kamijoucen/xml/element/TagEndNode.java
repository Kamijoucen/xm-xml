package com.kamijoucen.xml.element;

import com.kamijoucen.xml.build.BuilderVisitor;
import com.kamijoucen.xml.token.TokenLocation;

public class TagEndNode extends BaseTagNodeAdapter {

    public TagEndNode(String tagName, TokenLocation tokenLocation) {
        super.tokenLocation = tokenLocation;
        super.tagName = tagName;
    }


    @Override
    public String formatBuilder() {
        return null;
    }

    @Override
    public String builder(BuilderVisitor visitor) {
        return null;
    }
}
