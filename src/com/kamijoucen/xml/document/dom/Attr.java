package com.kamijoucen.xml.document.dom;

import com.kamijoucen.utils.StringUtils;
import com.kamijoucen.validate.Validate;

public class Attr extends NodeElement {

    private String key;
    private String val;

    public Attr(String key, String val) {
        Validate.notBlankVal(key);
        Validate.notBlankVal(val);
        this.key = key;
        this.val = val;
    }

    @Override
    public String gen() {
        return StringUtils.joinString(key, BUILT.ASSIGN, BUILT.STRING_DOUBLE, val, BUILT.STRING_DOUBLE);
    }

    @Override
    public String toString() {
        return gen();
    }
}
