package com.kamijoucen.xml.exception;

public class XmlSyntaxException extends RuntimeException {
    public XmlSyntaxException() {
    }

    public XmlSyntaxException(String message) {
        super(message);
    }

    public XmlSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlSyntaxException(Throwable cause) {
        super(cause);
    }

    public XmlSyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
