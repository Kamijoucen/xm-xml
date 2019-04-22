package com.kamijoucen.xml.ast;

import com.kamijoucen.common.callback.Convert;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;
import java.util.Map;

public abstract class BaseNormalNodeAdapter extends BaseNode implements NormalAst {

    // TODO: 2017/8/12 低效的数据结构，需要改为查询树
    protected Map<String, List<NormalAst>> body = CollecUtils.map();
    protected List<NormalAst> allBody = CollecUtils.list();
    protected List<TextNode> texts = CollecUtils.list();
    protected TokenLocation tokenLocation;

    protected void addChild(String key, NormalAst ast) {
        Validate.notBlankVal(key);
        Validate.notNull(ast);
        allBody.add(ast);
        List<NormalAst> vals = body.get(key);
        if (vals == null) {
            List<NormalAst> list = CollecUtils.list();
            list.add(ast);
            body.put(key, list);
        } else {
            vals.add(ast);
        }
    }

    @Override
    public NormalAst child(final String s) {
        Validate.notBlankVal(s);
        NormalAst c = CollecUtils.firstObj(body.get(s));
        return c == null ? NoneNormalNode.INSTANCE() : c;
    }

    @Override
    public List<NormalAst> childs(final String s) {
        Validate.notBlankVal(s);
        return body.get(s);
    }

    @Override
    public List<NormalAst> childs() {
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
