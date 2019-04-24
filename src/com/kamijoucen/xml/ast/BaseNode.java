package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

interface BaseNode {

    String toFormatString();

    String builder(TemplateBuilderVisitor visitor);

    TokenLocation getTokenLocation();
}
