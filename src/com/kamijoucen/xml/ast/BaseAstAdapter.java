package com.kamijoucen.xml.ast;

import com.kamijoucen.callback.Convert;
import com.kamijoucen.callback.Query;
import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.utils.StringUtils;
import com.kamijoucen.utils.Utils;
import com.kamijoucen.validate.Validate;
import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.ast.result.NoneResult;
import com.kamijoucen.xml.ast.result.TextResult;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;
import java.util.Map;

public abstract class BaseAstAdapter implements BaseAst {

    // TODO: 2017/8/12 低效的数据结构，需要改为查询树
    protected Map<String, List<BaseAst>> body = CollecUtils.map();
    protected List<BaseResult> attrs = CollecUtils.list();
    protected List<TextResult> texts = CollecUtils.list();
    protected TokenLocation tokenLocation;

    protected void addChild(String key, BaseAst ast) {
        List<BaseAst> vals = body.get(key);
        if (vals == null) {
            List<BaseAst> list = CollecUtils.list();
            list.add(ast);
            body.put(key, list);
        } else {
            vals.add(ast);
        }
    }

    @Override
    public BaseAst child(final String s) {
        Validate.notBlankVal(s);
        BaseAst c = CollecUtils.firstObj(body.get(s));
        return c == null ? NoneAst.INSTANCE : c;
    }

    @Override
    public List<BaseAst> childs(final String s) {
        Validate.notBlankVal(s);
        return body.get(s);
    }

    @Override
    public BaseResult attr(final String s) {
        BaseResult a = CollecUtils.find(attrs, new Query<BaseResult>() {
            @Override
            public boolean query(BaseResult o) {
                return StringUtils.equals(s, Utils.cast(o, AttrResult.class).getKey());
            }
        });
        return a == null ? NoneResult.INSTANCE : a;
    }

    @Override
    public List<BaseResult> attrs(final String s) {
        return CollecUtils.finds(attrs, new Query<BaseResult>() {
            @Override
            public boolean query(BaseResult o) {
                return StringUtils.equals(s, Utils.cast(o, AttrResult.class).getKey());
            }
        });
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
        return CollecUtils.convertList(texts, new Convert<TextResult, String>() {
            @Override
            public String convert(TextResult o) {
                return o.val();
            }
        });
    }

    @Override
    public String childText(int i) {
        if (CollecUtils.isEmptyCollection(texts) || texts.size() < i + 1) {
            return null;
        }
        return texts.get(i).val();
    }

    @Override
    public TokenLocation getTokenLocation() {
        return tokenLocation;
    }
}
