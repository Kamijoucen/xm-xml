package com.kamijoucen.xml.util;

import com.kamijoucen.xml.token.TokenType;

import static com.kamijoucen.xml.token.TokenType.*;

public class TokenUtil {

    public final static boolean[] endTokenFlags = new boolean[values().length];

    static {

        TokenType[] vals = values();
        for (TokenType val : vals) {
            switch (val) {
                case TAG_END:
                case SINGLE_TAG_END:
                case XML_HEAD_END:
                    endTokenFlags[val.value] = true;
                default:
            }
        }

    }

}
