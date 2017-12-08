package com.kamijoucen.xml.document;

import com.kamijoucen.xml.document.dom.Attr;
import com.kamijoucen.xml.document.dom.Block;
import com.kamijoucen.xml.document.dom.DomElement;

import java.util.List;
import java.util.Map;

public class DocumentTemplete {

    private Block root;
    private DocumentTemplete() {}

    public static DocumentTemplete create(String root) {
        DocumentTemplete templete = new DocumentTemplete();
        templete.root = DOM.node(root);
        return templete;
    }

    public DocumentTemplete attr(String key, String val) {
        root.attr(DOM.attr(key, val));
        return this;
    }

    public DocumentTemplete attr(Attr attr) {
        root.attr(attr);
        return this;
    }

    public DocumentTemplete attrs(List<Attr> attrs) {
        root.attrs(attrs);
        return this;
    }

    public DocumentTemplete attrs(Map<String, String> attrs) {
        root.attrs(DOM.attrs(attrs));
        return this;
    }

    public DocumentTemplete child(String childName) {
        root.child(DOM.node(childName));
        return this;
    }

    public DocumentTemplete child(DomElement element) {
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
