package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.xml.io.SimplePeekBufferReader2;
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
    private SimplePeekBufferReader2 input;
    private State state = State.NONE;
    private char currentStringToken = 0;
    private boolean textFlag = false;


    public DefaultScanner(InputStream stream) throws IOException {
        this.fileName = "local stream";
        input = new SimplePeekBufferReader2(new InputStreamReader(stream));
    }


    public DefaultScanner(InputStream stream, String charSet) throws IOException {
        this.fileName = "local stream";
        input = new SimplePeekBufferReader2(new InputStreamReader(stream, Charset.forName(charSet)));
    }

    public DefaultScanner(File file, String charSet) throws IOException {
        this.fileName = file.getName();
        input = new SimplePeekBufferReader2(new InputStreamReader(new FileInputStream(file), Charset.forName(charSet)));
    }

    public DefaultScanner(File file) throws IOException {
        this.fileName = file.getName();
        input = new SimplePeekBufferReader2(new InputStreamReader(new FileInputStream(file)));
    }

    public DefaultScanner(String xml) throws IOException {
        this.fileName = "local string";
        input = new SimplePeekBufferReader2(new InputStreamReader(new ByteArrayInputStream(xml.getBytes())));
    }

    public DefaultScanner(String xml, String charSet) throws IOException {
        this.fileName = "local string";
        input = new SimplePeekBufferReader2(new InputStreamReader(new ByteArrayInputStream(xml.getBytes(Charset.forName(charSet)))));
    }

    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public Token nextToken() {
        boolean matched = false;
        do {
            if (state != State.NONE) {
                matched = true;
            }
            switch (state) {
                case NONE:
                    nextChar();
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

    private void nextChar() {
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
        boolean isHasComment = false;
        do {
            for (; Character.isWhitespace(currentChar) && !isEndChar(currentChar); ) {
                nextChar();
            }
            handleComment();
            isHasComment = (currentChar == '<' && peekChar() == '!' && peekChar(2) == '-' && peekChar(3) == '-');
        } while (Character.isWhitespace(currentChar) || isHasComment);
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
        nextChar();
        while (currentChar != currentStringToken) {
            if (currentChar == '\0') {
                throw new XmlSyntaxException("错误位置:" + tokenLocation + "处字符串没有找到结束标识");
            }
            addCharToBuffer(currentChar);
            nextChar();
        }
        makeToken(TokenType.STRING, buffer.toString(), tokenLocation);
        nextChar();
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
                nextChar();
            }
        }
        makeToken(TokenType.TEXT, buffer.toString().trim(), tokenLocation);
    }

    private void handleComment() {
        tokenLocation = makeTokenLocation();
        if (currentChar == '<' && peekChar() == '!' && peekChar(2) == '-' && peekChar(3) == '-') {
            nextChar(); // eat <
            nextChar(); // eat !
            nextChar(); // eat -
            nextChar(); // eat -
            while (currentChar != '-' || peekChar() != '-') {
                nextChar();
            }
            nextChar();
            nextChar();
            if (currentChar != '>') {
                throw new XmlSyntaxException("错误位置:" + tokenLocation + "注释没有找到结束标志");
            }
            nextChar();
        }
    }


    private void handleTagStart() {
        char pch = (char) peekChar();
        if (pch == '/') {
            nextChar();
            addCharToBuffer(currentChar);
            makeToken(TokenType.TAG_END_START, buffer.toString(), tokenLocation);
            textFlag = false;
        } else if (pch == '!') {
            nextChar();
            addCharToBuffer(currentChar);
            if (peekChar() == '[') {
                handleCData();
            } else {
                handleDocType();
            }
            textFlag = true;
        } else if (pch == '?') {
            nextChar();
            addCharToBuffer(currentChar);
            makeToken(TokenType.XML_HEAD_START, buffer.toString(), tokenLocation);
            textFlag = false;
        } else {
            makeToken(TokenType.TAG_START, "<", tokenLocation);
            textFlag = false;
        }
    }

    private void handleCData() {
        nextChar();
        addCharToBuffer(currentChar);
        nextChar();
        while (Character.isUpperCase(currentChar) && !isEndChar(currentChar)) {
            addCharToBuffer(currentChar);
            nextChar();
        }
        if (!StringUtils.equals("<![CDATA", buffer.toString())) {
            throw new XmlSyntaxException("非法的 Unparsed Character Data 标识符:" + tokenLocation);
        }
        if (currentChar != '[') {
            throw new XmlSyntaxException("非法的 Unparsed Character Data 起始结构: CDATA后缺少[:" + tokenLocation);
        }
        nextChar();
        clearBuffer();
        boolean isFindEndFlag = false;
        while (!isEndChar(currentChar) && !isFindEndFlag) {
            if (currentChar == ']' && peekChar() == ']') {
                nextChar(); // eat ]
                nextChar(); // eat ]
                if (currentChar == '>') {
                    isFindEndFlag = true;
                } else {
                    addStringToBuffer("]]");
                }
            } else {
                addCharToBuffer(currentChar);
                nextChar();
            }
        }
        if (!isFindEndFlag) {
            throw new XmlSyntaxException("未找到 Unparsed Character Data 的结束标识符:" + tokenLocation);
        }
        makeToken(TokenType.TEXT, buffer.toString(), tokenLocation);
    }

    private void handleDocType() {
        nextChar();
        StringBuilder builder = new StringBuilder();
        while (Character.isUpperCase(currentChar)) {
            builder.append(currentChar);
            nextChar();
        }
        // TODO: 2018/3/22
        throw new IllegalStateException("不支持的调用");
    }

    private void handleTagEnd() {
        textFlag = true;
        makeToken(TokenType.TAG_END, ">", tokenLocation);
    }

    private void handleSingleTagEnd() {
        if ((char) peekChar() == '>') {
            textFlag = true;
            nextChar();
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
            nextChar();
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
        nextChar();
    }

    private void handleIdentifier() {
        tokenLocation = makeTokenLocation();
        addCharToBuffer(currentChar);
        nextChar();
        while (isIdentifierChar(currentChar)) {
            addCharToBuffer(currentChar);
            nextChar();
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
//            return peekChar() == '"' || peekChar() == '\'';
            return true;
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
