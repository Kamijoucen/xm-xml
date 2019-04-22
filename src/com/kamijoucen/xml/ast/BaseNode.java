package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;

public abstract class BaseNode {

    abstract String toFormatString();

    abstract String builder(TemplateBuilderVisitor visitor);

    abstract TokenLocation getTokenLocation();
}
