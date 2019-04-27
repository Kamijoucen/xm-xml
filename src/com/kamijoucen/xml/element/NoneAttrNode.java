package com.kamijoucen.xml.element;


import com.kamijoucen.xml.token.TokenLocation;

public class NoneAttrNode extends AttrNode {

    private static final NoneAttrNode NONENODE = new NoneAttrNode();

    private static TokenLocation tokenLocation = new TokenLocation(-1, -1, null);

    private NoneAttrNode() {
        super(tokenLocation);
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getVal() {
        return null;
    }

    @Override
    public void setKey(String key) {
    }

    @Override
    public void setVal(String val) {
    }

    static NoneAttrNode INSTANCE() {
        return NONENODE;
    }

}
