package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public class SingleTagStartAst extends TagStartAst {

    @Override
    public TagStartType startType() {
        return TagStartType.SINGLE;
    }

    public SingleTagStartAst(String tagName, List<AttrResult> attrs, TagStartType type, TokenLocation tokenLocation) {
        super(tagName, attrs, type, tokenLocation);
    }

}
