package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.xml.common.SimpleBufferReader;
import com.kamijoucen.xml.parser.Scanner;
import com.kamijoucen.xml.exception.FileAccessException;
import com.kamijoucen.xml.exception.XmlSyntaxException;
import com.kamijoucen.xml.token.Token;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.token.TokenType;

import java.io.*;
import java.nio.charset.Charset;

public class DefaultScanner implements Scanner {

    private long line = 1;
    private long column = 0;
    private String fileName;
    private char currentChar = 0;
    private Token token;
    private TokenLocation tokenLocation;
    private StringBuilder buffer = new StringBuilder();
    private SimpleBufferReader input;
    private State state = State.NONE;

    public DefaultScanner(String fileName, String charSet) throws FileNotFoundException {
        this.fileName = fileName;
        input = new SimpleBufferReader(new InputStreamReader(new FileInputStream(fileName), Charset.forName(charSet)));
    }

    public DefaultScanner(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        input = new SimpleBufferReader(new InputStreamReader(new FileInputStream(fileName)));
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public Token getNextToken() {
        boolean matched = false;
        do {
            if (state != State.NONE) {
                matched = true;
            }
            switch (state) {
                case NONE:
                    getNextChar();
                    break;
                case KEYWORDS:
                    handleKeyWords();
                    break;
                case IDENTIFIER:
                    handleIdentifier();
                    break;
                case STRING:
                    handleString();
                    break;
                case END_OF_FILE:
                    handleEndOfFile();
                    break;
            }
            if (currentChar == '\0') {
                state = State.END_OF_FILE;
            } else {
                preprocess();
                if (currentChar == '<' || currentChar == '>' || currentChar == '=' || currentChar == '/') {
                    state = State.KEYWORDS;
                } else if (currentChar == '\"' || currentChar == '\'') {
                    state = State.STRING;
                } else {
                    state = State.IDENTIFIER;
                }
            }
        } while (!matched);
        return token;
    }

    private void getNextChar() {
        int ch = 0;
        try {
            ch = input.read();
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
        if (ch == -1) {
            currentChar = '\0';
        } else {
            currentChar = (char) ch;
            if (currentChar == '\n') {
                ++line;
                column = 0;
            } else {
                ++column;
            }
        }
    }

    private void addCharToBuffer(char ch) {
        buffer.append(ch);
    }

    private int peekChar() {
        try {
            return input.peek();
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
    }

    private void preprocess() {
        for (; Character.isWhitespace(currentChar); ) {
            getNextChar();
        }
        // TODO: 2017/8/8 消除注释
    }

    @Override
    public void close() throws IOException {
        if (input != null) {
            input.close();
        }
    }

    // handle
    private void handleString() {
        tokenLocation = makeTokenLocation();
        // TODO: 2017/8/8
    }

    private void handleKeyWords() {
        tokenLocation = makeTokenLocation();
        addCharToBuffer(currentChar);
        if (currentChar == '<') {
            if ((char) peekChar() == '/') {
                getNextChar();
                addCharToBuffer(currentChar);
                makeToken(TokenType.TAG_END_START, buffer.toString(), tokenLocation);
            } else {
                makeToken(TokenType.TAG_START, "<", tokenLocation);
            }
        } else if (currentChar == '>') {
            makeToken(TokenType.TAG_END, ">", tokenLocation);
        } else if (currentChar == '/') {
            if ((char) peekChar() == '>') {
                getNextChar();
                addCharToBuffer(currentChar);
                makeToken(TokenType.SINGLE_TAG_END, buffer.toString(), tokenLocation);
            } else {
                throw new XmlSyntaxException("词法错误:标识符'/'后面只能出现'>'\t\t错误位置:" + tokenLocation);
            }
        } else {
            // TODO: 2017/8/8 属性
        }
        getNextChar();
    }

    private void handleIdentifier() {
        tokenLocation = makeTokenLocation();
        addCharToBuffer(currentChar);
        getNextChar();
        while (isIdentifierChar(currentChar)) {
            addCharToBuffer(currentChar);
            getNextChar();
        }
        makeToken(TokenType.IDENTIFIER, buffer.toString(), tokenLocation);
    }

    private void handleEndOfFile() {
        tokenLocation = makeTokenLocation();
        makeToken(TokenType.END_OF_FILE, "END_OF_FILE", tokenLocation);
    }

    // make token
    private Token makeToken(TokenType tokenType, String tokenVal, TokenLocation tokenLocation) {
        token = new Token(tokenLocation, tokenType, tokenVal);
        state = State.NONE;
        buffer.delete(0, buffer.length());
        return token;
    }

    private TokenLocation makeTokenLocation() {
        return new TokenLocation(line, column, fileName);
    }

    private boolean isIdentifierChar(char ch) {
        return Character.isAlphabetic(ch) || Character.isDigit(ch);
    }

}
