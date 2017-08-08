package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.core.CollecUtils;
import com.kamijoucen.validate.Validate;
import com.kamijoucen.xml.ast.TagEndStartAst;
import com.kamijoucen.xml.ast.TagStartAst;
import com.kamijoucen.xml.exception.XmlSyntaxException;
import com.kamijoucen.xml.parser.Scanner;
import com.kamijoucen.xml.result.DocumentResult;
import com.kamijoucen.xml.parser.Parser;
import com.kamijoucen.xml.result.TagBlockResult;
import com.kamijoucen.xml.result.TextResult;
import com.kamijoucen.xml.token.Token;
import com.kamijoucen.xml.token.TokenType;

import java.util.List;

public class DefaultParser implements Parser {

    private Scanner scanner;
    private List<TagBlockResult> docs = CollecUtils.list();

    public DefaultParser(Scanner scanner) {
        Validate.notNull(scanner);
        this.scanner = scanner;
    }

    @Override
    public DocumentResult parser() {
        while (scanner.getNextToken().getTokenType() != TokenType.END_OF_FILE) {
            docs.add(parserTagBlock());
        }
        return null;
    }

    private TagBlockResult parserTagBlock() {
        if (scanner.getToken().getTokenType() == TokenType.IDENTIFIER) {
            TextResult body = parserChildText();
            return null;
        }
        TagStartAst blockStart = parserTagStart();
        while (scanner.getToken().getTokenType() != TokenType.TAG_END_START) {
            TagBlockResult body = parserTagBlock();
        }
        TagEndStartAst blockEnd = parserTagEndStart();
        return null;
    }


    private TagEndStartAst parserTagEndStart() {
        if (scanner.getToken().getTokenType() != TokenType.TAG_END_START) {
            throw new XmlSyntaxException(scanner.getToken().getTokenLocation() + "处需要一个'</'");
        }
        Token tag = scanner.getNextToken();
        if (tag.getTokenType() != TokenType.IDENTIFIER) {
            throw new XmlSyntaxException(tag.getTokenLocation() + "处需要一个标签名字");
        }
        if (scanner.getNextToken().getTokenType() != TokenType.TAG_END) {
            throw new XmlSyntaxException(scanner.getToken().getTokenLocation() + "处需要一个标签结束符");
        }
        return new TagEndStartAst(tag.getStrVal(), tag.getTokenLocation());
    }

    private TagStartAst parserTagStart() {
        if (scanner.getToken().getTokenType() != TokenType.TAG_START) {
            throw new XmlSyntaxException(scanner.getToken().getTokenLocation() + "应该是一个标签起始符");
        }
        Token tag = scanner.getNextToken();
        if (tag.getTokenType() != TokenType.IDENTIFIER) {
            throw new XmlSyntaxException(tag.getTokenLocation() + "处需要一个标签名字");
        }
        if (scanner.getNextToken().getTokenType() != TokenType.TAG_END) {
            throw new XmlSyntaxException(scanner.getToken().getTokenLocation() + "处需要一个标签结束符");
        }
        scanner.getNextToken();
        // TODO: 2017/8/8 解析属性对
        return new TagStartAst(tag.getStrVal(), tag.getTokenLocation());
    }

    private TextResult parserChildText() {
        Token token = scanner.getToken();
        scanner.getNextToken();
        return new TextResult(token.getStrVal(), token.getTokenLocation());
    }

}
