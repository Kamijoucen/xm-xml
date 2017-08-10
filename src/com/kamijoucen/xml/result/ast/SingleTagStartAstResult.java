package com.kamijoucen.xml.result.ast;

import com.kamijoucen.xml.result.BaseResult;
import com.kamijoucen.xml.token.TokenLocation;

public class SingleTagStartAstResult extends TagStartAstResult {

    public SingleTagStartAstResult(String tagName, TokenLocation tokenLocation) {
        super(tagName, tokenLocation);
    }



}
