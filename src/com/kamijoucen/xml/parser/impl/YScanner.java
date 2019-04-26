package com.kamijoucen.xml.parser.impl;

import com.kamijoucen.xml.parser.Scanner;
import com.kamijoucen.xml.token.Token;

import java.io.*;
import java.io.IOException;


public class YScanner implements Scanner {

    private String xml;
    private int offset;
    private int line = 0;
    private int column = 0;

    public YScanner(String xml) {
        this.xml = xml;
    }

    public YScanner(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(file)), 5 * 1024 * 1024);
        String line;
        while((line = reader.readLine()) != null){
            builder.append(line);
        }
        this.xml = builder.toString();
    }

    @Override
    public Token getToken() {
        return null;
    }

    @Override
    public Token nextToken() {
        return null;
    }

    @Override
    public void close() throws IOException {
    }
}
