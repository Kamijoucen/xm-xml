package com.kamijoucen.xml.ast;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.ast.result.NoneResult;

import java.util.List;

public enum NoneAst implements BaseAst {
    INSTANCE;
    @Override
    public BaseAst child(String s) {
        return INSTANCE;
    }

    @Override
    public List<BaseAst> childs(String s) {
        return CollecUtils.readOnlyList();
    }

    @Override
    public BaseResult attr(String s) {
        return NoneResult.INSTANCE;
    }

    @Override
    public List<BaseAst> attrs(String s) {
        return CollecUtils.readOnlyList();
    }

    @Override
    public String firstChildText() {
        return null;
    }

    @Override
    public List<String> childText() {
        return CollecUtils.readOnlyList();
    }
}
