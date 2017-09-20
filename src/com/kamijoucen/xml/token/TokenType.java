package com.kamijoucen.xml.token;

public enum TokenType {
    TEXT,
    TAG_START,
    TAG_END,
    TAG_END_START,
    IDENTIFIER,
    END_OF_FILE,
    SINGLE_TAG_END,
    STRING,
    OPERATE,
    XML_HEAD_START,
    XML_HEAD_END
}
