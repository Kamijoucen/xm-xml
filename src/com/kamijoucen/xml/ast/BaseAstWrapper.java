package com.kamijoucen.xml.ast;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.utils.StringUtils;
import com.kamijoucen.utils.Utils;
import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.ast.result.NoneResult;
import com.kamijoucen.xml.ast.result.TextResult;

import java.util.List;

public abstract class BaseAstWrapper implements BaseAst {

    protected List<BaseAst> body = CollecUtils.list();
    protected List<BaseResult> attrs = CollecUtils.list();
    protected List<TextResult> texts = CollecUtils.list();

    @Override
    public BaseAst child(String s) {
        BaseAst c = CollecUtils.find(body, (o) -> StringUtils.equals(Utils.cast(o, TagBlockAst.class).getTagName(), s));
        return c == null ? NoneAst.INSTANCE : c;
    }

    @Override
    public List<BaseAst> childs(String s) {
        return CollecUtils.finds(body, (o) -> StringUtils.equals(Utils.cast(o, TagBlockAst.class).getTagName(), s));
    }

    @Override
    public BaseResult attr(String s) {
        BaseResult a = CollecUtils.find(attrs, (o) -> StringUtils.equals(s, Utils.cast(o, AttrResult.class).getKey()));
        return a == null ? NoneResult.INSTANCE : a;
    }

    @Override
    public List<BaseAst> attrs(String s) {
        return CollecUtils.finds(body, (o) -> StringUtils.equals(s, Utils.cast(o, AttrResult.class).getKey()));
    }

    @Override
    public String firstChildText() {
        return null;
    }

    @Override
    public List<String> childText() {
        return null;
    }

}
