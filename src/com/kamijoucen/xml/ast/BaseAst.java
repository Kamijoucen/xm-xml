package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.ast.result.BaseResult;

import java.util.List;

/**
 * 对于结构的抽象
 */
public interface BaseAst {
    BaseAst child(String s);

    List<BaseAst> childs(String s);

    BaseResult attr(String s);

    List<BaseAst> attrs(String s);

    String firstChildText();

    List<String> childText();

}
