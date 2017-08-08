package com.kamijoucen.xml.token;

public class TokenLocation {
    private long line;
    private long column;
    private String fileName;

    public TokenLocation(long line, long column, String fileName) {
        this.line = line;
        this.column = column;
        this.fileName = fileName;
    }

    public TokenLocation(long line, long column) {
        this.line = line;
        this.column = column;
    }

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }

    public long getColumn() {
        return column;
    }

    public void setColumn(long column) {
        this.column = column;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "{" +
                "line=" + line +
                ", column=" + column +
                '}';
    }
}
