package com.kamijoucen.xml.ast;

import com.kamijoucen.common.callback.Convert;
import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.utils.Utils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;
import java.util.Map;

public abstract class BaseTagNodeAdapter implements BaseNode, TagNode {

    protected String tagName;
    protected Map<String, List<TagNode>> body = CollecUtils.map();
    protected List<TagNode> allBody = CollecUtils.list();
    protected List<TextNode> texts = CollecUtils.list();
    protected List<AttrNode> attrs = CollecUtils.list();
    protected TokenLocation tokenLocation;

    @Override
    public String getTagName() {
        return tagName;
    }

    @Override
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public TagNode child(String s) {
        Validate.notBlankVal(s);
        TagNode c = CollecUtils.firstObj(body.get(s));
        return c == null ? NoneTagNode.INSTANCE() : c;
    }

    @Override
    public List<TagNode> childs(String s) {
        Validate.notBlankVal(s);
        return body.get(s);
    }

    @Override
    public List<TagNode> childs() {
        return allBody;
    }

    @Override
    public String firstChildText() {
        if (CollecUtils.isEmptyCollection(texts)) {
            return null;
        } else {
            return CollecUtils.firstObj(texts).val();
        }
    }

    @Override
    public List<String> childTexts() {
        return CollecUtils.convertList(texts, new Convert<TextNode, String>() {
            @Override
            public String convert(TextNode o) {
                return o.val();
            }
        });
    }

    @Override
    public String childText(int i) {
        TextNode text = CollecUtils.get(texts, i);
        return text == null ? null : text.val();
    }

    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    @Override
    public void addChild(TagNode node) {
        String name = node.getTagName();
        Validate.notBlankVal(name);
        Validate.notNull(node);
        allBody.add(node);
        List<TagNode> vals = body.get(name);
        if (vals == null) {
            List<TagNode> list = CollecUtils.list();
            list.add(node);
            body.put(name, list);
        } else {
            vals.add(node);
        }
    }

    @Override
    public void addAttr(AttrNode node) {
        this.attrs.add(node);
    }

    @Override
    public void addAttrs(List<AttrNode> list) {
        this.attrs.addAll(list);
    }

    @Override
    public void addChildText(TextNode node) {
        this.texts.add(node);
    }

    @Override
    public void addChildTexts(List<TextNode> nodes) {
        this.texts.addAll(nodes);
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
