package com.kamijoucen.xml.element;

import com.kamijoucen.xml.build.BuilderVisitor;
import com.kamijoucen.xml.token.TokenLocation;

public interface BaseNode {

    String formatBuilder();

    String builder(BuilderVisitor visitor);

    TokenLocation getTokenLocation();
}
