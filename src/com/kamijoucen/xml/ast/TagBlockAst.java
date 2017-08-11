package com.kamijoucen.xml.ast;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.xml.ast.result.AttrResult;
import com.kamijoucen.xml.ast.result.BaseResult;
import com.kamijoucen.xml.ast.result.TextResult;

import java.util.ArrayList;
import java.util.List;

public class TagBlockAst extends BaseAstWrapper {

    private String tagName;

    public TagBlockAst(String tagName) {
        this.tagName = tagName;
    }

    public void setAttrs(List<BaseResult> attrs) {
        this.attrs.addAll(attrs);
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

    public void addText(TextResult text) {
        texts.add(text);
    }

}
