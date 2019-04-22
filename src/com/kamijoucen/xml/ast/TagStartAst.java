package com.kamijoucen.xml.ast;

import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.utils.Utils;
import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.ast.result.NoneResult;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class TagStartAst extends BaseNormalAstAdapter {

    private String tagName;
    private TagStartType type;
    protected List<AttrAst> attrs = CollecUtils.list();

    public TagStartAst(String tagName, List<AttrAst> attrs, TagStartType type, TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
        this.type = type;
        this.tagName = tagName;
        this.attrs.addAll(attrs);
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<AttrAst> getAttrs() {
        return attrs;
    }

    public TagStartType getType() {
        return type;
    }

    public void setType(TagStartType type) {
        this.type = type;
    }

    @Override
    public String toFormatString() {
        return null;
    }

    @Override
    public String builder(TemplateBuilderVisitor visitor) {
        return null;
    }

    public enum TagStartType {
        BLOCK,
        SINGLE
    }

    public TagStartType startType() {
        return TagStartType.BLOCK;
    }


    @Override
    public AttrAst attr(final String s) {
        AttrAst a = CollecUtils.find(attrs, new Query<AttrAst>() {
            @Override
            public boolean query(AttrAst o) {
                return StringUtils.equals(s, Utils.cast(o, AttrAst.class).getKey());
            }
        });
        return a == null ? NoneAttrAst.INSTANCE() : a;
    }

    @Override
    public List<AttrAst> attrs(final String s) {
        return CollecUtils.finds(attrs, new Query<AttrAst>() {
            @Override
            public boolean query(AttrAst o) {
                return StringUtils.equals(s, Utils.cast(o, AttrAst.class).getKey());
            }
        });
    }


}
