package com.kamijoucen.xml.element;

import com.kamijoucen.xml.build.Visitor;
import com.kamijoucen.xml.token.TokenLocation;

public interface BaseNode {

//    String formatBuilder(FormatBuilderVisitor visitor);

    String builder(Visitor visitor);

    TokenLocation getTokenLocation();
}
