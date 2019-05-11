package com.kamijoucen.xml.token;

public enum TokenType {

    TEXT            (0),
    TAG_START       (1),
    TAG_END         (2),
    TAG_END_START   (3),
    IDENTIFIER      (4),
    END_OF_FILE     (5),
    SINGLE_TAG_END  (6),
    STRING          (7),
    OPERATE         (8),
    XML_HEAD_START  (9),
    XML_HEAD_END    (10);

    //----------------------

    public final int value;

    private TokenType(int value) {
        this.value = value;
    }
}
