package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.xml.io.SimpleBufferReader;
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
    private char currentStringToken = 0;

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
            if (state == State.NONE) {
                preprocess();
                if (currentChar == '\0') {
                    state = State.END_OF_FILE;
                } else {
                    if (isKeyWords(currentChar)) {
                        state = State.KEYWORDS;
                    } else if (currentChar == '\"' || currentChar == '\'') {
                        currentStringToken = currentChar;
                        state = State.STRING;
                    } else if (!isKeyWords(currentChar) && !Character.isWhitespace(currentChar)) {
                        state = State.IDENTIFIER;
                    } else {
                        System.out.println(currentChar + "--");
                        getNextChar();
                    }
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
        do {
            for (; Character.isWhitespace(currentChar) && currentChar != '\0'; ) {
                getNextChar();
            }
            handleComment();
        } while (Character.isWhitespace(currentChar));
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
        getNextChar();
        while (currentChar != currentStringToken) {
            if (currentChar == '\0') {
                throw new XmlSyntaxException("错误位置:" + tokenLocation + "处字符串没有找到结束标识");
            }
            addCharToBuffer(currentChar);
            getNextChar();
        }
        makeToken(TokenType.STRING, buffer.toString(), tokenLocation);
        getNextChar();
    }

    private void handleComment() {
        tokenLocation = makeTokenLocation();
        if (currentChar == '<' && peekChar() == '!') {
            getNextChar();
            getNextChar();
            if (currentChar != '-') {
                throw new XmlSyntaxException("错误位置:" + tokenLocation + "注释的起始标示有误");
            }
            getNextChar();
            if (currentChar != '-') {
                throw new XmlSyntaxException("错误位置:" + tokenLocation + "注释的起始标示有误");
            }
            getNextChar();
            while (currentChar != '-' || peekChar() != '-') {
                getNextChar();
            }
            getNextChar();
            getNextChar();
            if (currentChar != '>') {
                throw new XmlSyntaxException("错误位置:" + tokenLocation + "注释没有找到结束标志");
            }
            getNextChar();
        }
    }

    private void handleKeyWords() {
        tokenLocation = makeTokenLocation();
        addCharToBuffer(currentChar);
        if (currentChar == '<') {
            char pch = (char) peekChar();
            if (pch == '/') {
                getNextChar();
                addCharToBuffer(currentChar);
                makeToken(TokenType.TAG_END_START, buffer.toString(), tokenLocation);
            } else if (pch == '?') {
                getNextChar();
                addCharToBuffer(currentChar);
                makeToken(TokenType.XML_HEAD_START, buffer.toString(), tokenLocation);
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
                throw new XmlSyntaxException("错误位置:" + tokenLocation + "词法错误:标识符'/'后面只能出现'>'");
            }
        } else if (currentChar == '=') {
            makeToken(TokenType.OPERATE, buffer.toString(), tokenLocation);
        } else {  // ?
            if (peekChar() == '>') {
                getNextChar();
                addCharToBuffer(currentChar);
                makeToken(TokenType.XML_HEAD_END, buffer.toString(), tokenLocation);
            } else {
                throw new XmlSyntaxException("错误位置:" + tokenLocation + "处应该是 ?>");
            }
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
        makeToken(TokenType.IDENTIFIER, buffer.toString().trim(), tokenLocation);
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
        if (Character.isWhitespace(ch) || ch == '\0' || ch == '<' || ch == '>') {
            return false;
        } else if (ch == '=') {
            return peekChar() != '"' && peekChar() != '\'';
        } else {
            return ch != '?' || peekChar() != '>';
        }
    }

    private boolean isKeyWords(char ch) {
        return ch == '<' || ch == '>' || ch == '/' || ch == '=' || ch == '?';
    }
}
