package com.kamijoucen.xml.ast.result;


import com.kamijoucen.callback.Query;
import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.utils.StringUtils;
import com.kamijoucen.utils.Utils;
import com.kamijoucen.validate.Validate;
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
import java.io.InputStream;
import java.util.List;

public class DocumentResult {

    private XmlHeaderResult xmlHeader;
    private Scanner scanner;
    private Parser parser;
    private final List<TagBlockAst> roots = CollecUtils.list();

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

    public List<TagBlockAst> childs() {
        return roots;
    }


    public BaseAst child(final String str) {
        BaseAst root = CollecUtils.find(roots, new Query<TagBlockAst>() {
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
