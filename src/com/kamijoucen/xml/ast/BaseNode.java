package com.kamijoucen.xml.ast;

import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.build.TemplateBuilderVisitor;

public interface BaseNode {

    String toFormatString();

    String builder();

    TokenLocation getTokenLocation();
}
