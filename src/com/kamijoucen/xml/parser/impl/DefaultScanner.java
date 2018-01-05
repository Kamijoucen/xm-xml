package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.utils.StringUtils;
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
    private boolean textFlag = false;



    public DefaultScanner(File file, String charSet) throws IOException {
        this.fileName = file.getName();
        input = new SimpleBufferReader(new InputStreamReader(new FileInputStream(file), Charset.forName(charSet)));
    }

    public DefaultScanner(File file) throws IOException {
        this.fileName = file.getName();
        input = new SimpleBufferReader(new InputStreamReader(new FileInputStream(file)));
    }

    public DefaultScanner(String xml) throws IOException {
        this.fileName = "local string";
        input = new SimpleBufferReader(new InputStreamReader(new ByteArrayInputStream(xml.getBytes())));
    }

    public DefaultScanner(String xml, String charSet) throws IOException {
        this.fileName = "local string";
        input = new SimpleBufferReader(new InputStreamReader(new ByteArrayInputStream(xml.getBytes(Charset.forName(charSet)))));
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
                case TEXT:
                    handleText();
                    break;
                case END_OF_FILE:
                    handleEndOfFile();
                    break;
                default:
                    throw new XmlSyntaxException("ERROR");
            }
            if (state == State.NONE) {
                preprocess();
                if (currentChar == '\0') {
                    state = State.END_OF_FILE;
                } else {
                    if (isKeyWords(currentChar)) {
                        state = State.KEYWORDS;
                    } else if (textFlag) {
                        state = State.TEXT;
                    } else if (currentChar == '\"' || currentChar == '\'') {
                        currentStringToken = currentChar;
                        state = State.STRING;
                    } else if (StringUtils.isAlpha(currentChar) || currentChar == '_' || currentChar == ':') {
                        state = State.IDENTIFIER;
                    } else {
                        throw new XmlSyntaxException(tokenLocation + "处未知的字符 '" + currentChar + "'");
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

    private void addStringToBuffer(String str) {
        buffer.append(str);
    }

    private void addCharToBuffer(char ch) {
        buffer.append(ch);
    }

    private int peekChar(int i) {
        try {
            return input.peek(i);
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
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
            for (; Character.isWhitespace(currentChar) && !isEndChar(currentChar); ) {
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

    private void handleText() {
        tokenLocation = makeTokenLocation();
        while (true) {
            if (currentChar == '<' || isEndChar(currentChar)) {
                textFlag = false;
                break;
            } else if (currentChar == '\r' || currentChar == '\n') {
                preprocess();
                textFlag = currentChar != '<';
                break;
            } else {
                addCharToBuffer(currentChar);
                getNextChar();
            }
        }
        makeToken(TokenType.TEXT, buffer.toString().trim(), tokenLocation);
    }

    private void handleComment() {
        tokenLocation = makeTokenLocation();
        if (currentChar == '<' && peekChar() == '!' && peekChar(2) == '-' && peekChar(3) == '-') {
            getNextChar(); // eat <
            getNextChar(); // eat !
            getNextChar(); // eat -
            getNextChar(); // eat -
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


    private void handleTagStart() {
        char pch = (char) peekChar();
        if (pch == '/') {
            getNextChar();
            addCharToBuffer(currentChar);
            makeToken(TokenType.TAG_END_START, buffer.toString(), tokenLocation);
            textFlag = false;
        } else if (pch == '!') {
            getNextChar();
            addCharToBuffer(currentChar);
            if (peekChar() == '[') {
                handleCData();
            } else {
                handleDocType();
            }
            textFlag = true;
        } else if (pch == '?') {
            getNextChar();
            addCharToBuffer(currentChar);
            makeToken(TokenType.XML_HEAD_START, buffer.toString(), tokenLocation);
            textFlag = false;
        } else {
            makeToken(TokenType.TAG_START, "<", tokenLocation);
            textFlag = false;
        }
    }

    private void handleCData() {
        getNextChar();
        addCharToBuffer(currentChar);
        getNextChar();
        while (Character.isUpperCase(currentChar) && !isEndChar(currentChar)) {
            addCharToBuffer(currentChar);
            getNextChar();
        }
        if (!StringUtils.equals("<![CDATA", buffer.toString())) {
            throw new XmlSyntaxException("非法的 Unparsed Character Data 标识符:" + tokenLocation);
        }
        if (currentChar != '[') {
            throw new XmlSyntaxException("非法的 Unparsed Character Data 起始结构: CDATA后缺少[:" + tokenLocation);
        }
        getNextChar();
        clearBuffer();
        boolean flag = true;
        while (!isEndChar(currentChar) && flag) {
            if (currentChar == ']' && peekChar() == ']') {
                getNextChar(); // eat ]
                getNextChar(); // eat ]
                if (currentChar == '>') {
                    flag = false;
                } else {
                    addStringToBuffer("]]");
                }
            } else {
                addCharToBuffer(currentChar);
                getNextChar();
            }
        }
        makeToken(TokenType.TEXT, buffer.toString(), tokenLocation);
    }

    private void handleDocType() {
        // TODO: 2017/11/25
    }

    private void handleTagEnd() {
        textFlag = true;
        makeToken(TokenType.TAG_END, ">", tokenLocation);
    }

    private void handleSingleTagEnd() {
        if ((char) peekChar() == '>') {
            textFlag = true;
            getNextChar();
            addCharToBuffer(currentChar);
            makeToken(TokenType.SINGLE_TAG_END, buffer.toString(), tokenLocation);
        } else {
            throw new XmlSyntaxException("错误位置:" + tokenLocation + "词法错误:标识符'/'后面只能出现'>'");
        }
    }

    private void handleOp() {
        makeToken(TokenType.OPERATE, buffer.toString(), tokenLocation);
    }

    private void handleXmlHeadEnd() {
        if (peekChar() == '>') {
            getNextChar();
            addCharToBuffer(currentChar);
            makeToken(TokenType.XML_HEAD_END, buffer.toString(), tokenLocation);
        } else {
            throw new XmlSyntaxException("错误位置:" + tokenLocation + "处应该是 ?>");
        }
        textFlag = true;
    }

    private void handleKeyWords() {
        tokenLocation = makeTokenLocation();
        addCharToBuffer(currentChar);
        switch (currentChar) {
            case '<':
                handleTagStart();
                break;
            case '>':
                handleTagEnd();
                break;
            case '/':
                handleSingleTagEnd();
                break;
            case '=':
                handleOp();
                break;
            case '?':
                handleXmlHeadEnd();
                break;
            default:
                throw new XmlSyntaxException(tokenLocation + "处出现未识别的标识符");
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
        return StringUtils.isAlpha(ch) || Character.isDigit(ch) || ch == '-' || ch == '_' || ch == '.' || ch == ':';
    }

    private boolean isKeyWords(char ch) {
        if (ch == '<' || ch == '>' || (ch == '?' && peekChar() == '>')) {
            return true;
        } else if (ch == '=') {
            return peekChar() == '"' || peekChar() == '\'';
        } else {
            return ch == '/' && peekChar() == '>';
        }
    }

    private boolean isEndChar(char ch) {
        return ch == '\0';
    }

    private void clearBuffer() {
        buffer.delete(0, buffer.length());
    }

}
