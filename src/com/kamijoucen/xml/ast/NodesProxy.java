package com.kamijoucen.xml.ast;

public class NodesProxy {

    public static void addChild(String key, TagBlockNode node) {
        node.addChildNode(key, node);
    }

    public static void addChild(TagBlockNode node) {
        node.addChildNode(node);
    }

    public static void addAttr(String key, String val, TagStartNode node) {
        node.addAttr(key, val);
    }

    public static void addText(String text, TagBlockNode node) {
        TextNode textNode = new TextNode(text);
        node.addText(textNode);
    }
}
