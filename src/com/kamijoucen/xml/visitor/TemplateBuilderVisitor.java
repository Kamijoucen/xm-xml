package com.kamijoucen.xml.visitor;

import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.xml.ast.*;

public class TemplateBuilderVisitor {

    public String visit(AttrAst ast) {
        return StringUtils.joinString(ast.getKey(), BUILT.ASSIGN, BUILT.STRING_DOUBLE, ast.getVal(), BUILT.STRING_DOUBLE);
    }

    public String visit(SingleTagStartAst ast) {
        return null;
    }

    public String visit(TagBlockAst ast) {
        return null;
    }

    public String visit(TagEndAst ast) {
        return null;
    }

    public String visit(TagStartAst ast) {
        return null;
    }

    public String visit(TextAst ast) {
        return null;
    }

}
