package com.kamijoucen.xml.ast;

import java.util.List;

public interface TagNode {

    String getTagName();

    void setTagName(String name);


    //--------------------------------

    TagNode child(String str);

    List<TagNode> childs(String str);

    List<TagNode> childs();

    List<BaseNode> allNodes();

    AttrNode attr(String str);

    List<AttrNode> attrs(String str);

    List<AttrNode> attrs();

    String firstChildText();

    List<String> childTexts();

    String childText(int i);

    //--------------------------------

    void remove(TagNode node);

    void remove(TextNode node);

    void removeAttr(AttrNode node);

    //--------------------------------

    void addChild(TagNode node);

    //    void addChilds(String name, List<TagNode> list);

    //    void addChilds(List<TagNode> list);

    void addAttr(AttrNode node);

    void addAttrs(List<AttrNode> list);

    void addChild(TextNode node);

    void addChilds(List<TextNode> nodes);

}
