package com.kamijoucen.xml.ast;

import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class NoneNormalAst extends BaseNormalAstAdapter {

    private static final NoneNormalAst NONENORMALAST = new NoneNormalAst();

    private static TokenLocation tokenLocation = new TokenLocation(-1, -1, null);

    private NoneNormalAst() {}

    @Override
    public List<NormalAst> childs() {
        return CollecUtils.readOnlyList();
    }

    @Override
    public NormalAst child(String s) {
        return NONENORMALAST;
    }

    @Override
    public List<NormalAst> childs(String s) {
        return CollecUtils.readOnlyList();
    }

    @Override
    public AttrAst attr(String s) {
        return NoneAttrAst.INSTANCE();
    }

    @Override
    public List<AttrAst> attrs(String s) {
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


    public static NoneNormalAst INSTANCE() {
        return NONENORMALAST;
    }

    @Override
    String toFormatString() {
        return null;
    }

    @Override
    String builder(TemplateBuilderVisitor visitor) {
        return null;
    }

    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }


}
