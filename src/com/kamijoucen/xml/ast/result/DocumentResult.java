package com.kamijoucen.xml.ast.result;


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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class DocumentResult {

    private Scanner scanner;
    private Parser parser;
    private final List<TagBlockAst> roots = CollecUtils.list();

    public static DocumentResult load(String fileName) {
        return new DocumentResult(fileName);
    }

    private DocumentResult(String fileName) {
        try {
            this.scanner = new DefaultScanner(fileName);
        } catch (FileNotFoundException e) {
            throw new FileAccessException(e);
        }
        parser = new LLParser(scanner);
        parserAll();
        try {
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BaseAst child(String str) {
        BaseAst root = CollecUtils.find(roots, (o) -> StringUtils.equals(str, o.getTagName()));
        if (root == null) {
            return NoneAst.INSTANCE;
        } else {
            return root;
        }
    }

    public void format() {
        // TODO: 2017/8/10
    }

    public void addRoot(TagBlockAst ast) {
        roots.add(ast);
    }

    private void parserAll() {
        while (scanner.getToken().getTokenType() != TokenType.END_OF_FILE) {
            roots.add(Utils.cast(parser.parserTagBlock()));
        }
    }

}
