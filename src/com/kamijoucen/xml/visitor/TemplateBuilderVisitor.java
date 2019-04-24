package com.kamijoucen.xml.visitor;

import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.xml.ast.*;

public class TemplateBuilderVisitor {

    public String visit(AttrNode node) {
        return StringUtils.joinString(node.getKey(), BUILT.ASSIGN, BUILT.STRING_DOUBLE, node.getVal(), BUILT.STRING_DOUBLE);
    }

    public String visit(TagBlockNode node) {
        StringBuilder builder = new StringBuilder();

        return null;
    }

    public String visit(TextNode node) {
        String text = node.getText();
        return StringUtils.joinString(BUILT.CDATA_START, node.getText(), BUILT.CDATA_END);
    }

}
