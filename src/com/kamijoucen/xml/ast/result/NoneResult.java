package com.kamijoucen.xml.ast.result;

public enum NoneResult implements BaseResult {
    INSTANCE;
    @Override
    public String val() {
        return null;
    }
}
