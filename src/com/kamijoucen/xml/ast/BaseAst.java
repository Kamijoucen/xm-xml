package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public interface BaseAst {
    BaseAst child(String str);

    List<BaseAst> childs(String str);

    BaseResult attr(String str);

    List<BaseAst> attrs(String str);

    String firstChildText();

    List<String> childTexts();

    String childText(int i);

    TokenLocation getTokenLocation();
}
