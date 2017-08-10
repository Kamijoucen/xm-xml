package com.kamijoucen.xml.ast;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.ast.result.TextResult;

import java.util.ArrayList;
import java.util.List;

public class TagBlockAst implements BaseAst {

    private String tagName;
    // TODO: 2017/8/9 低效的数据结构
    private List<AttrResult> attrs = new ArrayList<AttrResult>();
    private List<BaseAst> body = CollecUtils.list();
    private List<TextResult> texts = CollecUtils.list();

    public TagBlockAst(String tagName) {
        this.tagName = tagName;
    }

    public void putAttr(String key, String val) {
        attrs.add(new AttrResult(key, val));
    }

    public void addChild(BaseAst b) {
        body.add(b);
    }

    public String getTagName() {
        return tagName;
    }


    public List<BaseAst> getBody() {
        return body;
    }

    public List<AttrResult> getAttrs() {
        return attrs;
    }

    public void addText(TextResult text) {
        texts.add(text);
    }

    @Override
    public BaseAst child(String s) {
        return null;
    }

    @Override
    public List<BaseAst> childs(String s) {
        return null;
    }

    @Override
    public BaseResult attr(String s) {
        return null;
    }

    @Override
    public List<BaseAst> attrs(String s) {
        return null;
    }

    @Override
    public String firstChildText() {
        return null;
    }

    @Override
    public List<String> childText() {
        return null;
    }
}
