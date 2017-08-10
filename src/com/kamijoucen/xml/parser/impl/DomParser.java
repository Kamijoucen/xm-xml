package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.utils.StringUtils;
import com.kamijoucen.validate.Validate;
import com.kamijoucen.xml.result.*;
import com.kamijoucen.xml.result.ast.SingleTagStartAstResult;
import com.kamijoucen.xml.result.ast.TagEndStartAstResult;
import com.kamijoucen.xml.result.ast.TagStartAstResult;
import com.kamijoucen.xml.exception.XmlSyntaxException;
import com.kamijoucen.xml.parser.Scanner;
import com.kamijoucen.xml.parser.Parser;
import com.kamijoucen.xml.token.Token;
import com.kamijoucen.xml.token.TokenType;

import java.util.List;

public class DomParser implements Parser {

    private Scanner scanner;
    private List<BaseResult> docs = CollecUtils.list();

    public DomParser(Scanner scanner) {
        Validate.notNull(scanner);
        this.scanner = scanner;
        scanner.getNextToken();
    }

    @Override
    public DocumentResult parser() {
        while (scanner.getToken().getTokenType() != TokenType.END_OF_FILE) {
            docs.add(parserTagBlock());
        }
        BaseResult query = CollecUtils.find(((TagBlockResult) CollecUtils.firstObj(docs)).getBody(), (b) -> b instanceof TagBlockResult && StringUtils.equals(((TagBlockResult) b).getTagName(), "FrequentlyUsedFonts"));
        return null;
    }

    private BaseResult parserTagBlock() {
        if (scanner.getToken().getTokenType() == TokenType.IDENTIFIER) { // 文本节点
            TextResult body = parserChildText();
            return body;
        }
        TagStartAstResult blockStart = parserTagStart();
        if (blockStart instanceof SingleTagStartAstResult) {  // 是单标签
            return blockStart;
        }
        TagBlockResult blockResult = new TagBlockResult(blockStart.getTagName(), null);
        while (scanner.getToken().getTokenType() != TokenType.TAG_END_START) {
            BaseResult body = parserTagBlock();
            blockResult.addChild(body);
        }
        TagEndStartAstResult blockEnd = parserTagEndStart();
        if (!blockEnd.getTagName().equals(blockStart.getTagName())) {
            throw new XmlSyntaxException(blockEnd.getTokenLocation() + "处应该出现</" + blockStart.getTagName() + ">");
        }
        return blockResult;
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
        scanner.getNextToken();
        while (scanner.getToken().getTokenType() != TokenType.TAG_END
                && scanner.getToken().getTokenType() != TokenType.SINGLE_TAG_END
                && scanner.getToken().getTokenType() != TokenType.END_OF_FILE) {
            AttrResult attr = parserAttr();
            System.out.println(attr);
        }

        TokenType end = scanner.getToken().getTokenType();
        scanner.getNextToken();
        if (end == TokenType.TAG_END) {
            return new TagStartAstResult(tag.getStrVal(), tag.getTokenLocation());
        } else if (end == TokenType.SINGLE_TAG_END) {
            return new SingleTagStartAstResult(tag.getStrVal(), tag.getTokenLocation());
        } else {
            throw new XmlSyntaxException(scanner.getToken().getTokenLocation() + "处需要一个标签结束符");
        }
    }

    private AttrResult parserAttr() {
        Token key = scanner.getToken();
        Token op = scanner.getNextToken();
        if (op.getTokenType() == TokenType.OPERATE) {
            Token val = scanner.getNextToken();
            if (val.getTokenType() == TokenType.STRING) {
                scanner.getNextToken();
                return new AttrResult(key.getStrVal(), val.getStrVal(), key.getTokenLocation());
            } else {
                throw new XmlSyntaxException(key.getTokenLocation() + "属性没有找到属性值");
            }
        } else if (op.getTokenType() == TokenType.IDENTIFIER
                || op.getTokenType() == TokenType.TAG_END
                || op.getTokenType() == TokenType.SINGLE_TAG_END) {
            return new AttrResult(key.getStrVal(), "", key.getTokenLocation());
        } else {
            throw new XmlSyntaxException(key.getTokenLocation() + "属性后存在未识别的标识符");
        }
    }

    private TextResult parserChildText() {
        Token token = scanner.getToken();
        scanner.getNextToken();
        return new TextResult(token.getStrVal(), token.getTokenLocation());
    }

}
