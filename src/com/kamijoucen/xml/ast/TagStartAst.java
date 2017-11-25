package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public class TagStartAst extends BaseAstAdapter {

    private String tagName;
    private TagStartType type;

    public TagStartAst(String tagName, List<AttrResult> attrs, TagStartType type, TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
        this.type = type;
        this.tagName = tagName;
        this.attrs.addAll(attrs);
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<BaseResult> getAttrs() {
        return attrs;
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

    public TagStartType startType() {
        return TagStartType.BLOCK;
    }


}
