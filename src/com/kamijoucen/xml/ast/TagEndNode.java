package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class TagEndNode extends BaseNormalNodeAdapter {

    private String tagName;
    private TokenLocation tokenLocation;

    public TagEndNode(String tagName, TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    @Override
    public NormalAst child(String s) {
        return null;
    }

    @Override
    public List<NormalAst> childs(String s) {
        return null;
    }

    @Override
    public List<NormalAst> childs() {
        return null;
    }

    @Override
    public AttrNode attr(String s) {
        return null;
    }

    @Override
    public List<AttrNode> attrs(String s) {
        return null;
    }

    @Override
    public String firstChildText() {
        return null;
    }

    @Override
    public List<String> childTexts() {
        return null;
    }

    @Override
    public String childText(int i) {
        return null;
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
