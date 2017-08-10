package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public class SingleTagStartAst extends TagStartAst {

    public SingleTagStartAst(String tagName, List<AttrResult> attrs) {
        super(tagName, attrs);
    }

}
