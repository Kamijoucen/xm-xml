package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.core.CollecUtils;
import com.kamijoucen.validate.Validate;
import com.kamijoucen.xml.result.BaseResult;
import com.kamijoucen.xml.result.ast.TagEndStartAstResult;
import com.kamijoucen.xml.result.ast.TagStartAstResult;
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
    private List<BaseResult> docs = CollecUtils.list();

    public DefaultParser(Scanner scanner) {
        Validate.notNull(scanner);
        this.scanner = scanner;
        scanner.getNextToken();
    }

    @Override
    public DocumentResult parser() {
        while (scanner.getToken().getTokenType() != TokenType.END_OF_FILE) {
            docs.add(parserTagBlock());
        }
        return null;
    }

    private BaseResult parserTagBlock() {
        if (scanner.getToken().getTokenType() == TokenType.IDENTIFIER) { // 文本节点
            TextResult body = parserChildText();
            return body;
        }
        TagStartAstResult blockStart = parserTagStart();
        TagBlockResult blockResult = new TagBlockResult(blockStart.getTagName(), null);
        while (scanner.getToken().getTokenType() != TokenType.TAG_END_START) {
            BaseResult body = parserTagBlock();
            blockResult.addChild(body);
        }
        TagEndStartAstResult blockEnd = parserTagEndStart();
        if (!blockEnd.getTagName().equals(blockStart.getTagName())) {
            throw new XmlSyntaxException(blockEnd.getTokenLocation() + "处应该出现</" + blockStart.getTagName() + ">");
        }
        return null;
    }


    private TagEndStartAstResult parserTagEndStart() {
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
        scanner.getNextToken();
        return new TagEndStartAstResult(tag.getStrVal(), tag.getTokenLocation());
    }

    private TagStartAstResult parserTagStart() {
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
        return new TagStartAstResult(tag.getStrVal(), tag.getTokenLocation());
    }

    private TextResult parserChildText() {
        Token token = scanner.getToken();
        scanner.getNextToken();
        return new TextResult(token.getStrVal(), token.getTokenLocation());
    }

}
