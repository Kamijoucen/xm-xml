package com.kamijoucen.xml.ast;

import java.util.List;

public interface TagNode {

    String getTagName();

    TagNode child(String str);

    List<TagNode> childs(String str);

    List<TagNode> childs();

    AttrNode attr(String str);

    List<AttrNode> attrs(String str);

    String firstChildText();

    List<String> childTexts();

    String childText(int i);

}
