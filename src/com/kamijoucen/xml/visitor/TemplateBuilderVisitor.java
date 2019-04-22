package com.kamijoucen.xml.visitor;

import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.xml.ast.*;

public class TemplateBuilderVisitor {

    public String visit(AttrNode ast) {
        return StringUtils.joinString(ast.getKey(), BUILT.ASSIGN, BUILT.STRING_DOUBLE, ast.getVal(), BUILT.STRING_DOUBLE);
    }

    public String visit(SingleTagStartNode ast) {
        return null;
    }

    public String visit(TagBlockNode ast) {
        return null;
    }

    public String visit(TagEndNode ast) {
        return null;
    }

    public String visit(TagStartNode ast) {
        return null;
    }

    public String visit(TextNode ast) {
        return null;
    }

}
