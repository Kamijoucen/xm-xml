package com.kamijoucen.xml.document.dom;

import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.ast.result.DOM;

import java.util.List;
import java.util.Map;

public class SingleBlock extends BaseBlock {

    public SingleBlock(String nodeName) {
        Validate.notBlankVal(nodeName);
        this.nodeName = nodeName;
    }

    public SingleBlock attr(String key, String val) {
        attrs.add(DOM.attr(key, val));
        return this;
    }

    public SingleBlock attr(Attr attr) {
        attrs.add(attr);
        return this;
    }

    public SingleBlock attrs(List<Attr> attrs) {
        this.attrs.addAll(attrs);
        return this;
    }

    public SingleBlock attrs(Map<String, String> attrs) {
        this.attrs.addAll(DOM.attrs(attrs));
        return this;
    }


    @Override
    public String gen() {
        StringBuilder builder = new StringBuilder()
                .append(BUILT.TAG_START).append(nodeName);
        if (attrs.size() != 0) {
            builder.append(BUILT.SPACE);
            genAttrs(builder);
        }
        builder.append(BUILT.TAG_SINGLE_END);
        return builder.toString();
    }

//    @Override
    protected String formatGen(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int a = 0; a < i; ++a) {
            stringBuilder.append("  ");
        }
        stringBuilder.append(gen());
        return stringBuilder.toString();
    }
}
