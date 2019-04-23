package com.kamijoucen.xml.ast;

import java.util.List;

public interface NormalNode {

    NormalNode child(String str);

    List<NormalNode> childs(String str);

    List<NormalNode> childs();

    AttrNode attr(String str);

    List<AttrNode> attrs(String str);

    String firstChildText();

    List<String> childTexts();

    String childText(int i);

}
