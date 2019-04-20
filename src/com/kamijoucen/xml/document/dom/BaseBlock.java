package com.kamijoucen.xml.document.dom;


import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;

import java.util.List;

public abstract class BaseBlock extends DomElement {
    protected String nodeName;
    protected List<Attr> attrs = CollecUtils.list();

    protected void genAttrs(StringBuilder builder) {
        builder.append(StringUtils.joinSepString(BUILT.SPACE, attrs));
    }

}
