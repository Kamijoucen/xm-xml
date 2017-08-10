package com.kamijoucen.xml.ast;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public class TagStartAst implements BaseAst {

    private String tagName;
    private List<AttrResult> attrs = CollecUtils.list();

    public TagStartAst(String tagName, List<AttrResult> attrs) {
        this.tagName = tagName;
        this.attrs = attrs;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<AttrResult> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<AttrResult> attrs) {
        this.attrs = attrs;
    }

    @Override
    public BaseAst child(String s) {
        return null;
    }

    @Override
    public List<BaseAst> childs(String s) {
        return null;
    }

    @Override
    public BaseResult attr(String s) {
        return null;
    }

    @Override
    public List<BaseAst> attrs(String s) {
        return null;
    }

    @Override
    public String firstChildText() {
        return null;
    }

    @Override
    public List<String> childText() {
        return null;
    }
}
