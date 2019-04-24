package com.kamijoucen.xml.ast;

import java.util.List;

public interface TagNode {

    String getTagName();

    void setTagName(String name);

    TagNode child(String str);

    void addChild(TagNode node);

    List<TagNode> childs(String str);

//    void addChilds(String name, List<TagNode> list);

    List<TagNode> childs();

//    void addChilds(List<TagNode> list);

    AttrNode attr(String str);

    void addAttr(AttrNode node);

    List<AttrNode> attrs(String str);

    void addAttrs(List<AttrNode> list);

    String firstChildText();

    void addChildText(TextNode node);

    List<String> childTexts();

    void addChildTexts(List<TextNode> nodes);

    String childText(int i);

}
