package com.kamijoucen.xml.ast;

import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class NoneTagNode extends BaseTagNodeAdapter {

    private static final NoneTagNode NONENORMALAST = new NoneTagNode();

    private static TokenLocation tokenLocation = new TokenLocation(-1, -1, null);

    private NoneTagNode() {}

    @Override
    public List<TagNode> childs() {
        return CollecUtils.readOnlyList();
    }

    @Override
    public TagNode child(String s) {
        return NONENORMALAST;
    }

    @Override
    public List<TagNode> childs(String s) {
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


    public static NoneTagNode INSTANCE() {
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
