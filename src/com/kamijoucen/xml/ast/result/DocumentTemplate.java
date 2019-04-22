package com.kamijoucen.xml.ast.result;

import com.kamijoucen.xml.document.dom.Attr;
import com.kamijoucen.xml.document.dom.Block;
import com.kamijoucen.xml.document.dom.DomElement;

import java.util.List;
import java.util.Map;

public class DocumentTemplate {

    private Block root;
    private DocumentTemplate() {}

    public static DocumentTemplate create(String root) {
        DocumentTemplate templete = new DocumentTemplate();
        templete.root = DOM.node(root);
        return templete;
    }

    public DocumentTemplate attr(String key, String val) {
        root.attr(DOM.attr(key, val));
        return this;
    }

    public DocumentTemplate attr(Attr attr) {
        root.attr(attr);
        return this;
    }

    public DocumentTemplate attrs(List<Attr> attrs) {
        root.attrs(attrs);
        return this;
    }

    public DocumentTemplate attrs(Map<String, String> attrs) {
        root.attrs(DOM.attrs(attrs));
        return this;
    }

    public DocumentTemplate child(String childName) {
        root.child(DOM.node(childName));
        return this;
    }

    public DocumentTemplate child(DomElement element) {
        root.child(element);
        return this;
    }

    public String buildString() {
        // todo 处理head和doctype
        return root.gen();
    }

    public String buildFormatString() {
        // todo
        return "";
    }

    public void buildFile(String path) {
        // todo 处理head和doctype
    }

    public void buildFormatFile(String path) {
        // todo
    }

}
