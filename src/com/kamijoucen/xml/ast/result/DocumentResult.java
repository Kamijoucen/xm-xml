package com.kamijoucen.xml.ast.result;


import com.kamijoucen.core.QueryCallBack;
import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.utils.StringUtils;
import com.kamijoucen.utils.Utils;
import com.kamijoucen.xml.ast.BaseAst;
import com.kamijoucen.xml.ast.NoneAst;
import com.kamijoucen.xml.ast.TagBlockAst;
import com.kamijoucen.xml.exception.FileAccessException;
import com.kamijoucen.xml.parser.Parser;
import com.kamijoucen.xml.parser.Scanner;
import com.kamijoucen.xml.parser.impl.DefaultScanner;
import com.kamijoucen.xml.parser.impl.LLParser;
import com.kamijoucen.xml.token.TokenType;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DocumentResult {

    private XmlHeaderResult xmlHeader;
    private Scanner scanner;
    private Parser parser;
    private final List<TagBlockAst> roots = CollecUtils.list();

    public static DocumentResult loadFile(String fileName) {
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


    public BaseAst child(final String str) {
        BaseAst root = CollecUtils.find(roots, new QueryCallBack<TagBlockAst>() {
            @Override
            public boolean query(TagBlockAst o) {
                return StringUtils.equals(str, o.getTagName());
            }
        });
        return root == null ? NoneAst.INSTANCE : root;
    }

    public void format() {
        // TODO: 2017/8/10
    }

    private void parserAll() {
        if (scanner.getToken().getTokenType() == TokenType.XML_HEAD_START) {
            this.xmlHeader = parser.parserXmlHeader();
        }
        while (scanner.getToken().getTokenType() != TokenType.END_OF_FILE) {
            roots.add(Utils.cast(parser.parserTagBlock(), TagBlockAst.class));
        }
    }

    private Object executeXPath(String str) {
        // TODO: 2017/8/11
        return null;
    }

}
