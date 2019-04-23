package com.kamijoucen.xml.ast.result;


import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.utils.Utils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.ast.*;
import com.kamijoucen.xml.exception.FileAccessException;
import com.kamijoucen.xml.parser.Parser;
import com.kamijoucen.xml.parser.Scanner;
import com.kamijoucen.xml.parser.impl.DefaultScanner;
import com.kamijoucen.xml.parser.impl.LLParser;
import com.kamijoucen.xml.token.TokenType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DocumentResult {

    private XmlHeaderNode xmlHeader;
    private Scanner scanner;
    private Parser parser;
    private final List<TagNode> roots = CollecUtils.list();

    public static DocumentResult loadStream(InputStream stream, String charset) {
        Validate.notNull(stream);
        Validate.notBlankVal(charset);
        DocumentResult result = new DocumentResult();
        try {
            result.scanner = new DefaultScanner(stream, charset);
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
        result.parse();
        return result;
    }


    public static DocumentResult loadStream(InputStream stream) {
        Validate.notNull(stream);
        DocumentResult result = new DocumentResult();
        try {
            result.scanner = new DefaultScanner(stream);
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
        result.parse();
        return result;
    }

    public static DocumentResult loadFile(String fileName) {
        Validate.notBlankVal(fileName);
        DocumentResult result = new DocumentResult();
        try {
            result.scanner = new DefaultScanner(new File(fileName));
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
        result.parse();
        return result;
    }

    public static DocumentResult loadFile(String filename, String charset) {
        Validate.notBlankVal(filename);
        Validate.notBlankVal(charset);
        DocumentResult result = new DocumentResult();
        try {
            result.scanner = new DefaultScanner(new File(filename, charset));
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
        result.parse();
        return result;
    }

    public static DocumentResult loadString(String xml) {
        Validate.notBlankVal(xml);
        DocumentResult result = new DocumentResult();
        try {
            result.scanner = new DefaultScanner(xml);
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
        result.parse();
        return result;
    }

    public static DocumentResult loadString(String xml, String charset) {
        Validate.notBlankVal(xml);
        Validate.notBlankVal(charset);
        DocumentResult result = new DocumentResult();
        try {
            result.scanner = new DefaultScanner(xml, charset);
        } catch (IOException e) {
            throw new FileAccessException(e);
        }
        result.parse();
        return result;
    }

    private DocumentResult() {}



//    private DocumentResult(String fileName, String charset) {
//        try {
//            if (charset == null) {
//                this.scanner = new DefaultScanner(new File(fileName));
//            } else {
//                this.scanner = new DefaultScanner(new File(fileName), charset);
//            }
//        } catch (IOException e) {
//            throw new FileAccessException(e);
//        }
//    }

    private void parse() {
        parser = new LLParser(scanner);
        parserAll();
        try {
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner = null;
            parser = null;
        }
    }

    public List<TagNode> childs() {
        return roots;
    }


    public TagNode child(final String str) {
        TagNode root = CollecUtils.find(roots, new Query<TagNode>() {
            @Override
            public boolean query(TagNode o) {
                return StringUtils.equals(str, Utils.cast(o, TagBlockNode.class).getTagName());
            }
        });
        return root == null ? NoneTagNode.INSTANCE() : root;
    }

    public void format() {
        // TODO: 2017/8/10
    }

    private void parserAll() {
        if (scanner.getToken().getTokenType() == TokenType.XML_HEAD_START) {
            this.xmlHeader = parser.parserXmlHeader();
        }
        while (scanner.getToken().getTokenType() != TokenType.END_OF_FILE) {
            roots.add(Utils.cast(parser.parserTagBlock(), TagBlockNode.class));
        }
    }

    private Object executeXPath(String str) {
        // TODO: 2017/8/11
        return null;
    }

}
