package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public interface BaseAst {
    BaseAst child(String str);

    List<BaseAst> childs(String str);

    List<BaseAst> childs();

    BaseResult attr(String str);

    List<BaseResult> attrs(String str);

    String firstChildText();

    List<String> childTexts();

    String childText(int i);

    TokenLocation getTokenLocation();
}
