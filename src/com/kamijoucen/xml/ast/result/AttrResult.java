package com.kamijoucen.xml.ast.result;

import com.kamijoucen.xml.token.TokenLocation;

public class AttrResult extends BaseResultAdapter {

    private String key;
    private String value;

    public AttrResult(String key, String val, TokenLocation tokenLocation) {
        this.key = key;
        this.value = val;
        this.tokenLocation = tokenLocation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "attr{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public String val() {
        return value;
    }
}
