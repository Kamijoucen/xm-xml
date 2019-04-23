package com.kamijoucen.xml.ast;

import com.kamijoucen.common.callback.Convert;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;
import java.util.Map;

public abstract class BaseNormalNodeAdapter extends BaseNode implements NormalNode {

    // TODO: 2017/8/12 低效的数据结构，需要改为查询树
    protected Map<String, List<NormalNode>> body = CollecUtils.map();
    protected List<NormalNode> allBody = CollecUtils.list();
    protected List<TextNode> texts = CollecUtils.list();
    protected TokenLocation tokenLocation;

    protected void addChild(String key, NormalNode ast) {
        Validate.notBlankVal(key);
        Validate.notNull(ast);
        allBody.add(ast);
        List<NormalNode> vals = body.get(key);
        if (vals == null) {
            List<NormalNode> list = CollecUtils.list();
            list.add(ast);
            body.put(key, list);
        } else {
            vals.add(ast);
        }
    }

    @Override
    public NormalNode child(final String s) {
        Validate.notBlankVal(s);
        NormalNode c = CollecUtils.firstObj(body.get(s));
        return c == null ? NoneNormalNode.INSTANCE() : c;
    }

    @Override
    public List<NormalNode> childs(final String s) {
        Validate.notBlankVal(s);
        return body.get(s);
    }

    @Override
    public List<NormalNode> childs() {
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
}
