package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.element.*;
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
        scanner.nextToken();
    }

    @Override
    public TagBlockNode parserTagBlock() {
        TagBlockNode block = parserBlockTagStart();
        if (block.getType() == TagBlockNode.TagStartType.SINGLE) {
            return block;
        }
        while (scanner.getToken().getTokenType() != TokenType.TAG_END_START) {
            switch (scanner.getToken().getTokenType()) {
                case TEXT:
                    block.addChild(parserChildText());
                    break;
                case TAG_START:
                    block.addChild(parserTagBlock());
                    break;
                case END_OF_FILE:
                    throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + " <"
                            + block.getTagName() + "> 标签(" + block.getTokenLocation()
                            + ")未找到匹配的结束标签就遇到文件结束");
                default:
                    throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation()
                            + " 标签的子节点只能是文本或者其他标签但是出现了" + scanner.getToken().getTokenType());
            }
        }
        TagEndNode blockEnd = parserTagEnd();
        if (!blockEnd.getTagName().equals(block.getTagName())) {
            throw new XmlSyntaxException("错误位置:" + blockEnd.getTokenLocation()
                    + "处标签嵌套不正确，应该匹配 <"
                    + block.getTagName() + "> (" + block.getTokenLocation() + ") 的结束标签");
        }
        return block;
    }

    @Override
    public TagEndNode parserTagEnd() {
        if (scanner.getToken().getTokenType() != TokenType.TAG_END_START) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + "处需要一个 '</', "
                    + "但是现在出现了 '" + scanner.getToken().getStrVal() + "'");
        }
        TokenLocation endLocation = scanner.getToken().getTokenLocation();
        Token tag = scanner.nextToken();
        if (tag.getTokenType() != TokenType.IDENTIFIER) {
            throw new XmlSyntaxException("错误位置:" + tag.getTokenLocation() + "处需要一个标签名, 但是现在出现了 '"
                    + scanner.getToken().getStrVal() + "'");
        }
        if (scanner.nextToken().getTokenType() != TokenType.TAG_END) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + "处需要一个标签结束符 '>', "
                    + "但是现在出现了 '" + scanner.getToken().getStrVal() + "'");
        }
        scanner.nextToken();
        return new TagEndNode(tag.getStrVal(), endLocation);
    }

    @Override
    public TagBlockNode parserBlockTagStart() {
        if (scanner.getToken().getTokenType() != TokenType.TAG_START) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + "应该是一个标签起始符 '<', "
                    + "但是现在出现了 '" + scanner.getToken().getStrVal() + "'");
        }
        Token location = scanner.getToken();
        Token tag = scanner.nextToken();
        if (tag.getTokenType() != TokenType.IDENTIFIER) {
            throw new XmlSyntaxException("错误位置:" + tag.getTokenLocation() + "处需要一个标签名, 但是现在出现了 '"
                    + scanner.getToken().getStrVal() + "'");
        }
        scanner.nextToken();
        List<AttrNode> attrs = CollecUtils.list();
        if (isTagEndToken(scanner.getToken().getTokenType())
                && scanner.getToken().getTokenType() != TokenType.IDENTIFIER) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation()
                    + "标签内部只允许出现属性, 但出现了 '" + scanner.getToken().getStrVal() + "'");
        }
        while (isTagEndToken(scanner.getToken().getTokenType())) {
            attrs.add(parserAttr());
        }

        TokenType end = scanner.getToken().getTokenType();
        scanner.nextToken();
        if (end == TokenType.TAG_END) {
            return new TagBlockNode(tag.getStrVal(), attrs, TagBlockNode.TagStartType.BLOCK, location.getTokenLocation());
        } else if (end == TokenType.SINGLE_TAG_END) {
            return new TagBlockNode(tag.getStrVal(), attrs, TagBlockNode.TagStartType.SINGLE, location.getTokenLocation());
        } else {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation()
                    + "处需要一个标签结束符 '>' | '/>', 但是现在出现了 '" + scanner.getToken().getStrVal() + "'");
        }
    }

    @Override
    public AttrNode parserAttr() {
        Token key = scanner.getToken();
        Token op = scanner.nextToken();
        if (op.getTokenType() == TokenType.OPERATE) {
            Token val = scanner.nextToken();
            if (val.getTokenType() == TokenType.STRING) {
                scanner.nextToken();
                return new AttrNode(key.getStrVal(), val.getStrVal(), key.getTokenLocation());
            } else {
                throw new XmlSyntaxException("错误位置:" + key.getTokenLocation() + "属性没有找到属性值");
            }
        } else if (op.getTokenType() == TokenType.IDENTIFIER
                || op.getTokenType() == TokenType.TAG_END
                || op.getTokenType() == TokenType.SINGLE_TAG_END
                || op.getTokenType() == TokenType.XML_HEAD_END) {
            // TODO: 2017/8/23 这个不允许出现单独的属性名
            return new AttrNode(key.getStrVal(), "", key.getTokenLocation());
        } else {
            throw new XmlSyntaxException("错误位置:" + key.getTokenLocation() + "属性后存在未识别的标识符");
        }
    }

    @Override
    public TextNode parserChildText() {
        Token token = scanner.getToken();
        scanner.nextToken();
        return new TextNode(token.getStrVal(), token.getTokenLocation());
    }

    @Override
    public XmlHeaderNode parserXmlHeader() {
        if (scanner.getToken().getTokenType() != TokenType.XML_HEAD_START) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation()
                    + "标签头应该是 '<?' 开头并由 '?>' 结束");
        }
        if (scanner.nextToken().getTokenType() != TokenType.IDENTIFIER
                && !StringUtils.equals(scanner.getToken().getStrVal().toLowerCase(), "xml")) {
            throw new XmlSyntaxException("错误位置:" + scanner.getToken().getTokenLocation() + "应该声明文档类型是 xml");
        }
        scanner.nextToken();
        XmlHeaderNode result = new XmlHeaderNode();
        while (scanner.getToken().getTokenType() != TokenType.XML_HEAD_END
                && scanner.getToken().getTokenType() != TokenType.END_OF_FILE) {
            result.addAttr(parserAttr());
        }
        scanner.nextToken();
        return result;
    }

    private boolean isTagEndToken(TokenType type) {
        return type != TokenType.TAG_END && type != TokenType.SINGLE_TAG_END && type != TokenType.XML_HEAD_END;
    }

}
