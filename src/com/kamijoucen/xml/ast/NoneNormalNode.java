package com.kamijoucen.xml.ast;

import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class NoneNormalNode extends BaseNormalNodeAdapter {

    private static final NoneNormalNode NONENORMALAST = new NoneNormalNode();

    private static TokenLocation tokenLocation = new TokenLocation(-1, -1, null);

    private NoneNormalNode() {}

    @Override
    public List<NormalNode> childs() {
        return CollecUtils.readOnlyList();
    }

    @Override
    public NormalNode child(String s) {
        return NONENORMALAST;
    }

    @Override
    public List<NormalNode> childs(String s) {
        return CollecUtils.readOnlyList();
    }

    @Override
    public AttrNode attr(String s) {
        return NoneAttrNode.INSTANCE();
    }

    @Override
    public List<AttrNode> attrs(String s) {
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


    public static NoneNormalNode INSTANCE() {
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
