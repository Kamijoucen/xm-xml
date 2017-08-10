package com.kamijoucen.xml.result;

import com.kamijoucen.xml.token.TokenLocation;

import java.util.ArrayList;
import java.util.List;

public class TagBlockResult extends BaseResult {

    private String tagName;
    // TODO: 2017/8/9 低效的数据结构
    private List<AttrResult> attrs = new ArrayList<AttrResult>();
    private List<BaseResult> body = new ArrayList<BaseResult>();

    public TagBlockResult(String tagName, TokenLocation tokenLocation) {
        super(tokenLocation);
        this.tagName = tagName;
    }

    public void putAttr(String key, String val, TokenLocation tokenLocation) {
        attrs.add(new AttrResult(key, val, tokenLocation));
    }

    public void addChild(BaseResult b) {
        body.add(b);
    }

    public String getTagName() {
        return tagName;
    }


    public List<BaseResult> getBody() {
        return body;
    }

    public List<AttrResult> getAttrs() {
        return attrs;
    }
}
