package com.kamijoucen.xml.io;

public class IOUtil {

    public final static boolean[] firstIdentifierFlags = new boolean[256];
    public final static boolean[] identifierFlags      = new boolean[256];
    public final static boolean[] keyFlags             = new boolean[256];
    public final static char[]    DIGITS               = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    static {

        for (char c = 0; c < keyFlags.length; ++c) {
            if (c == '<'
                    || c == '>'
                    || c == '?'
                    || c == '='
                    || c == '/') {
                keyFlags[c] = true;
            }
        }

        for (char c = 0; c < firstIdentifierFlags.length; ++c) {
            if (c >= 'A' && c <= 'Z') {
                firstIdentifierFlags[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                firstIdentifierFlags[c] = true;
            } else if (c == '_' || c == ':') {
                firstIdentifierFlags[c] = true;
            }
        }

        for (char c = 0; c < identifierFlags.length; ++c) {
            if (c >= 'A' && c <= 'Z') {
                identifierFlags[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                identifierFlags[c] = true;
            } else if (c == '_' || c == ':' || c == '.' || c == '-') {
                identifierFlags[c] = true;
            } else if (c >= '0' && c <= '9') {
                identifierFlags[c] = true;
            }
        }
    }

}
