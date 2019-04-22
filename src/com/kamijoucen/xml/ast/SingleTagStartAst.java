package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class SingleTagStartAst extends TagStartAst {

    @Override
    public TagStartType startType() {
        return TagStartType.SINGLE;
    }

    public SingleTagStartAst(String tagName, List<AttrAst> attrs, TagStartType type, TokenLocation tokenLocation) {
        super(tagName, attrs, type, tokenLocation);
    }

    @Override
    public String toFormatString() {
        return super.toFormatString();
    }

    @Override
    public String builder(TemplateBuilderVisitor visitor) {
        return super.builder(visitor);
    }
}
