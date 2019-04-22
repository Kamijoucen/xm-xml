package com.kamijoucen.xml.ast;


import com.kamijoucen.xml.token.TokenLocation;

public class NoneAttrAst extends AttrAst {

    private static final NoneAttrAst noneAst = new NoneAttrAst();

    private static TokenLocation tokenLocation = new TokenLocation(-1, -1, null);

    private NoneAttrAst() {
        super(null, null, tokenLocation);
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getVal() {
        return null;
    }

    public static NoneAttrAst INSTANCE() {
        return noneAst;
    }

}
