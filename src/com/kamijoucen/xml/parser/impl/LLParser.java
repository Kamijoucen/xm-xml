package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.validate.Validate;
import com.kamijoucen.xml.ast.*;
import com.kamijoucen.xml.ast.result.*;
import com.kamijoucen.xml.exception.XmlSyntaxException;
import com.kamijoucen.xml.parser.Scanner;
import com.kamijoucen.xml.parser.Parser;
import com.kamijoucen.xml.token.Token;
import com.kamijoucen.xml.token.TokenLocation;
import com.kamijoucen.xml.token.TokenType;

import java.util.List;

public class LLParser implements Parser {

    private Scanner scanner;

    public LLParser(Scanner scanner) {
        Validate.notNull(scanner);
        this.scanner = scanner;
        scanner.getNextToken();
    }

    @Override
    public BaseAst parserTagBlock() {
        TagStartAst blockStart = parserTagStart();
        TagBlockAst blockAst = new TagBlockAst(blockStart.getTagName());
        blockAst.setStart(blockStart);
        if (blockStart instanceof SingleTagStartAst) {
            blockAst.setAttrs(blockStart.getAttrs());
            return blockAst;
        }
        blockAst.setAttrs(blockStart.getAttrs());
        while (scanner.getToken().getTokenType() != TokenType.TAG_END_START) {
            switch (scanner.getToken().getTokenType()) {
                case IDENTIFIER:
                    TextResult text = parserChildText();
                    blockAst.addText(text);
                    break;
                case TAG_START:
                    BaseAst cb = parserTagBlock();
                    blockAst.addChild( cb);
                    break;
                case END_OF_FILE:
                    throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + " <"
                            + blockStart.getTagName() + "> 标签(" + blockStart.getTokenLocation()
                            + ")未找到匹配的结束标签就遇到文件结束");
                default:
                    throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation()
                            + " 标签的子节点只能是文本或者其他标签");
            }
        }
        TagEndAst blockEnd = parserTagEnd();
        if (!blockEnd.getTagName().equals(blockStart.getTagName())) {
            throw new XmlSyntaxException("错误位置:" + blockEnd.getTokenLocation()
                    + "处标签嵌套不正确，应该匹配 <"
                    + blockStart.getTagName() + "> (" + blockStart.getTokenLocation() + ") 的结束标签");
        }
        blockAst.setEnd(blockEnd);
        return blockAst;
    }


    public TagEndAst parserTagEnd() {
        if (scanner.getToken().getTokenType() != TokenType.TAG_END_START) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + "处需要一个 '</'");
        }
        TokenLocation endLocation = scanner.getToken().getTokenLocation();
        Token tag = scanner.getNextToken();
        if (tag.getTokenType() != TokenType.IDENTIFIER) {
            throw new XmlSyntaxException("错误位置:" + tag.getTokenLocation() + "处需要一个标签名字");
        }
        if (scanner.getNextToken().getTokenType() != TokenType.TAG_END) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + "处需要一个标签结束符 '>'");
        }
        scanner.getNextToken();
        return new TagEndAst(tag.getStrVal(), endLocation);
    }

    @Override
    public TagStartAst parserTagStart() {
        if (scanner.getToken().getTokenType() != TokenType.TAG_START) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + "应该是一个标签起始符 '<'");
        }
        Token location = scanner.getToken();
        Token tag = scanner.getNextToken();
        if (tag.getTokenType() != TokenType.IDENTIFIER) {
            throw new XmlSyntaxException("错误位置:" + tag.getTokenLocation() + "处需要一个标签名字");
        }
        scanner.getNextToken();
        List<AttrResult> attrs = CollecUtils.list();
        if (!isTagEndToken(scanner.getToken().getTokenType())
                && scanner.getToken().getTokenType() != TokenType.IDENTIFIER) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation()
                    + "标签内部只允许出现属性,但出现了 '" + scanner.getToken().getStrVal() + "'");
        }
        while (!isTagEndToken(scanner.getToken().getTokenType())) {
            attrs.add(parserAttr());
        }

        TokenType end = scanner.getToken().getTokenType();
        scanner.getNextToken();
        if (end == TokenType.TAG_END) {
            return new TagStartAst(tag.getStrVal(), attrs, TagStartAst.TagStartType.BLOCK, location.getTokenLocation());
        } else if (end == TokenType.SINGLE_TAG_END) {
            return new SingleTagStartAst(tag.getStrVal(), attrs, TagStartAst.TagStartType.SINGLE, location.getTokenLocation());
        } else {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation()
                    + "处需要一个标签结束符 '>' | '/>'");
        }
    }

    @Override
    public AttrResult parserAttr() {
        Token key = scanner.getToken();
        Token op = scanner.getNextToken();
        if (op.getTokenType() == TokenType.OPERATE) {
            Token val = scanner.getNextToken();
            if (val.getTokenType() == TokenType.STRING) {
                scanner.getNextToken();
                return new AttrResult(key.getStrVal(), val.getStrVal(), key.getTokenLocation());
            } else {
                throw new XmlSyntaxException("错误位置:" + key.getTokenLocation() + "属性没有找到属性值");
            }
        } else if (op.getTokenType() == TokenType.IDENTIFIER
                || op.getTokenType() == TokenType.TAG_END
                || op.getTokenType() == TokenType.SINGLE_TAG_END
                || op.getTokenType() == TokenType.XML_HEAD_END) {
            return new AttrResult(key.getStrVal(), "", key.getTokenLocation());
        } else {
            throw new XmlSyntaxException("错误位置:" + key.getTokenLocation() + "属性后存在未识别的标识符");
        }
    }

    @Override
    public TextResult parserChildText() {
        Token token = scanner.getToken();
        scanner.getNextToken();
        return new TextResult(token.getStrVal(), token.getTokenLocation());
    }

    @Override
    public XmlHeaderResult parserXmlHeader() {
        if (scanner.getToken().getTokenType() != TokenType.XML_HEAD_START) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation()
                    + "标签头应该是 '<?' 开头并由 '?>' 结束");
        }
        if (scanner.getNextToken().getTokenType() != TokenType.IDENTIFIER
                && !scanner.getToken().getStrVal().toLowerCase().equals("xml")) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + "应该声明文档类型是 xml");
        }
        scanner.getNextToken();
        XmlHeaderResult result = new XmlHeaderResult();
        while (scanner.getToken().getTokenType() != TokenType.XML_HEAD_END
                && scanner.getToken().getTokenType() != TokenType.END_OF_FILE) {
            result.addAttr(parserAttr());
        }
        scanner.getNextToken();
        return result;
    }

    private boolean isTagEndToken(TokenType type) {
        return type == TokenType.TAG_END || type == TokenType.SINGLE_TAG_END || type == TokenType.XML_HEAD_END;
    }

}
