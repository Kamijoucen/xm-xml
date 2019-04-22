package com.kamijoucen.xml.document.dom;

import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.ast.result.DOM;

import java.util.List;
import java.util.Map;

public class Block extends BaseBlock {

    private List<DomElement> childs = CollecUtils.list();

    public Block(String nodeName) {
        Validate.notBlankVal(nodeName);
        this.nodeName = nodeName;
    }

    public Block attr(String key, String val) {
        attrs.add(DOM.attr(key, val));
        return this;
    }

    public Block attr(Attr attr) {
        attrs.add(attr);
        return this;
    }

    public Block attrs(List<Attr> attrs) {
        this.attrs.addAll(attrs);
        return this;
    }

    public Block attrs(Map<String, String> attrs) {
        this.attrs.addAll(DOM.attrs(attrs));
        return this;
    }

    public Block child(String childName) {
        childs.add(DOM.node(childName));
        return this;
    }

    public Block child(DomElement element) {
        childs.add(element);
        return this;
    }

    private String genBlockStart() {
        StringBuilder builder = new StringBuilder(32)
                .append(BUILT.TAG_START).append(nodeName);
        if (attrs.size() != 0) {
            builder.append(BUILT.SPACE);
            genAttrs(builder);
        }
        builder.append(BUILT.TAG_END);
        return builder.toString();
    }

    private String genBlockEnd() {
        return StringUtils.joinString(BUILT.TAG_END_START, nodeName, BUILT.TAG_END);
    }

    @Override
    public String gen() {
        StringBuilder builder = new StringBuilder(genBlockStart());
        for (DomElement c : childs) {
            builder.append(c.gen());
        }
        builder.append(genBlockEnd());
        return builder.toString();
    }

}
