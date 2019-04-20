package com.kamijoucen.xml.document.dom;

import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.utils.Utils;

public class Text extends DomElement {

    private String text;

    public Text(String text) {
        this.text = Utils.nullDefVal(text, "");
    }

    @Override
    public String gen() {
        return StringUtils.joinString(BUILT.CDATA_START, text, BUILT.CDATA_END);
    }
}
