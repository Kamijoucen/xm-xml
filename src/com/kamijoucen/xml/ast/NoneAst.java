package com.kamijoucen.xml.ast;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.ast.result.NoneResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public enum NoneAst implements BaseAst {
    INSTANCE;

    @Override
    public List<BaseAst> childs() {
        return CollecUtils.readOnlyList();
    }

    private static TokenLocation tokenLocation = new TokenLocation(-1, -1, null);
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
    public List<BaseResult> attrs(String s) {
        return CollecUtils.readOnlyList();
    }

    @Override
    public String firstChildText() {
        return null;
    }

    @Override
    public List<String> childTexts() {
        return CollecUtils.readOnlyList();
    }

    @Override
    public String childText(int i) {
        return null;
    }


    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }


}
