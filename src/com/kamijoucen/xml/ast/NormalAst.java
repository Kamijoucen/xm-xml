package com.kamijoucen.xml.ast;

import java.util.List;

public interface NormalAst {

    NormalAst child(String str);

    List<NormalAst> childs(String str);

    List<NormalAst> childs();

    AttrNode attr(String str);

    List<AttrNode> attrs(String str);

    String firstChildText();

    List<String> childTexts();

    String childText(int i);

}
