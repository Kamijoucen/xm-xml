package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.BaseResult;

import java.util.List;

public interface NormalAst {

    NormalAst child(String str);

    List<NormalAst> childs(String str);

    List<NormalAst> childs();

    AttrAst attr(String str);

    List<AttrAst> attrs(String str);

    String firstChildText();

    List<String> childTexts();

    String childText(int i);

}
