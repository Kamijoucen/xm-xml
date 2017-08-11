package com.kamijoucen.xml.parser;

import com.kamijoucen.xml.ast.BaseAst;
import com.kamijoucen.xml.ast.TagEndStartAst;
import com.kamijoucen.xml.ast.TagStartAst;
import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.TextResult;

public interface Parser {

    BaseAst parserTagBlock();

    TagEndStartAst parserTagEndStart();

    TagStartAst parserTagStart();

    AttrResult parserAttr();

    TextResult parserChildText();


}
