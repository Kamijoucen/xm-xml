package com.kamijoucen.xml.result;

import com.kamijoucen.xml.token.TokenLocation;

public class AttrResult extends BaseResult {

    private String key;
    private String value;

    public AttrResult(String key, String val, TokenLocation tokenLocation) {
        super(tokenLocation);
        this.key = key;
        this.value = val;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttrResult that = (AttrResult) o;

        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
