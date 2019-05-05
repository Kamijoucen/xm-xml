package com.kamijoucen.xml.element;

import com.kamijoucen.common.callback.Convert;
import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.utils.Utils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseTagNodeAdapter implements TagNode {

    protected String tagName;
    protected Map<String, List<TagNode>> groupTags = CollecUtils.map();
    protected List<TagNode> tags = CollecUtils.list();
    protected List<TextNode> texts = CollecUtils.list();
    protected List<AttrNode> attrs = CollecUtils.list();
    protected List<BaseNode> allNodes = CollecUtils.list();
    protected TokenLocation tokenLocation;

    @Override
    public String getTagName() {
        return tagName;
    }

    @Override
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    //-----------------------------------

    @Override
    public TagNode child(String s) {
        Validate.notBlankVal(s);
        TagNode c = CollecUtils.firstObj(groupTags.get(s));
        return c == null ? NoneTagNode.INSTANCE() : c;
    }

    @Override
    public TagNode child(String s, int i) {
        Validate.notBlankVal(s);
        return CollecUtils.get(groupTags.get(s), i);
    }

    @Override
    public List<TagNode> childs(String s) {
        Validate.notBlankVal(s);
        return new ArrayList<TagNode>(groupTags.get(s));
    }

    @Override
    public List<TagNode> childs() {
        return new ArrayList<TagNode>(tags);
    }

    @Override
    public List<BaseNode> allNodes() {
        return new ArrayList<BaseNode>(allNodes);
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

    @Override
    public List<AttrNode> attrs() {
        return new ArrayList<AttrNode>(attrs);
    }

    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }

    //-----------------------------------

    @Override
    public void remove(TagNode node) {
    }

    @Override
    public void remove(TextNode node) {
    }

    @Override
    public void removeAttr(AttrNode node) {
    }


    //-----------------------------------

    @Override
    public void addChild(TagNode node) {
        String name = node.getTagName();
        Validate.notBlankVal(name);
        Validate.notNull(node);
        tags.add(node);
        allNodes.add((BaseNode) node);
        List<TagNode> vals = groupTags.get(name);
        if (vals == null) {
            List<TagNode> list = CollecUtils.list();
            list.add(node);
            groupTags.put(name, list);
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
    public void addChild(TextNode node) {
        this.allNodes.add(node);
        this.texts.add(node);
    }

    @Override
    public void addChilds(List<TextNode> nodes) {
        this.allNodes.addAll(nodes);
        this.texts.addAll(nodes);
    }

}
