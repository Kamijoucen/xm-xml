package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;

import java.util.List;

public class TagStartAst extends BaseAstWrapper {

    private String tagName;

    public TagStartAst(String tagName, List<AttrResult> attrs) {
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

}
