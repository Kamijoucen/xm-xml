package com.kamijoucen.xml.parser;

import com.kamijoucen.xml.ast.*;

public interface Parser {

    TagBlockNode parserTagBlock();

    TagEndNode parserTagEnd();

    TagBlockNode parserBlockTagStart();

    AttrNode parserAttr();

    TextNode parserChildText();

    XmlHeaderNode parserXmlHeader();

}
