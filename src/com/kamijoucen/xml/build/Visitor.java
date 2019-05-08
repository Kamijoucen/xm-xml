package com.kamijoucen.xml.build;

import com.kamijoucen.xml.element.AttrNode;
import com.kamijoucen.xml.element.TagBlockNode;
import com.kamijoucen.xml.element.TextNode;

public interface Visitor {

    String visit(AttrNode node);

    String visit(TagBlockNode node);

    String visit(TextNode node);

}
