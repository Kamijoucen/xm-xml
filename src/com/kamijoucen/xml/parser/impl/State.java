package com.kamijoucen.xml.parser.impl;

public enum State {
    NONE,
    END_OF_FILE,
    IDENTIFIER,
    KEYWORDS,
    STRING,
    TEXT
}
