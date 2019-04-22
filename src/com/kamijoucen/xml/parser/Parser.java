package com.kamijoucen.xml.parser;

import com.kamijoucen.xml.ast.*;
import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.TextResult;
import com.kamijoucen.xml.ast.result.XmlHeaderResult;

public interface Parser {

    NormalAst parserTagBlock();

    TagEndAst parserTagEnd();

    TagStartAst parserTagStart();

    AttrAst parserAttr();

    TextAst parserChildText();

    XmlHeaderAst parserXmlHeader();

}
