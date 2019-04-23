package com.kamijoucen.xml.parser;

import com.kamijoucen.xml.ast.*;

public interface Parser {

    NormalNode parserTagBlock();

    TagEndNode parserTagEnd();

    TagStartNode parserTagStart();

    AttrNode parserAttr();

    TextNode parserChildText();

    XmlHeaderNode parserXmlHeader();

}
