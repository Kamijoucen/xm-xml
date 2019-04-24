package com.kamijoucen.xml.ast;

import com.kamijoucen.common.callback.Convert;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;
import java.util.Map;

public abstract class BaseTagNodeAdapter extends BaseNode implements TagNode {

    protected Map<String, List<TagNode>> body = CollecUtils.map();
    protected List<TagNode> allBody = CollecUtils.list();
    protected List<TextNode> texts = CollecUtils.list();
    protected TokenLocation tokenLocation;

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

    void addChildNode(String key, TagNode node) {
        Validate.notBlankVal(key);
        Validate.notNull(node);
        allBody.add(node);
        List<TagNode> vals = body.get(key);
        if (vals == null) {
            List<TagNode> list = CollecUtils.list();
            list.add(node);
            body.put(key, list);
        } else {
            vals.add(node);
        }
    }

    void addChildNode(TagNode node) {
        Validate.notNull(node);
        allBody.add(node);
        List<TagNode> vals = body.get(node.getTagName());
        if (vals == null) {
            List<TagNode> list = CollecUtils.list();
            list.add(node);
            body.put(node.getTagName(), list);
        } else {
            vals.add(node);
        }
    }

    void addTextNode(TextNode node) {
        Validate.notNull(node);
        texts.add(node);
    }
}
