package com.kamijoucen.xml.ast;

import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.utils.Utils;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

import java.util.List;

public class TagStartNode extends BaseTagNodeAdapter {

    private String tagName;
    private TagStartType type;
    protected List<AttrNode> attrs = CollecUtils.list();

    public TagStartNode(String tagName, List<AttrNode> attrs, TagStartType type, TokenLocation tokenLocation) {
        this.tokenLocation = tokenLocation;
        this.type = type;
        this.tagName = tagName;
        this.attrs.addAll(attrs);
    }

    @Override
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<AttrNode> getAttrs() {
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
    public AttrNode attr(final String s) {
        AttrNode a = CollecUtils.find(attrs, new Query<AttrNode>() {
            @Override
            public boolean query(AttrNode o) {
                return StringUtils.equals(s, Utils.cast(o, AttrNode.class).getKey());
            }
        });
        return a == null ? NoneAttrNode.INSTANCE() : a;
    }

    @Override
    public List<AttrNode> attrs(final String s) {
        return CollecUtils.finds(attrs, new Query<AttrNode>() {
            @Override
            public boolean query(AttrNode o) {
                return StringUtils.equals(s, Utils.cast(o, AttrNode.class).getKey());
            }
        });
    }


}
