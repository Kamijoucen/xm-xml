package com.kamijoucen.xml.element;

import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.build.BuilderVisitor;

import java.util.List;

public class DocumentTemplate {

    private TagBlockNode root = null;

    private DocumentTemplate() {
    }

    public static DocumentTemplate createDocument(String rootTagName) {
        Validate.notBlankVal(rootTagName);
        DocumentTemplate document = new DocumentTemplate();
        document.root = new TagBlockNode(rootTagName);
        return document;
    }

    public String builder() {
        BuilderVisitor visitor = new BuilderVisitor();
        return root.builder(visitor);
    }


    public void addChild(TagNode node) {
        root.addChild(node);
    }


    public void addAttr(AttrNode node) {
        root.addAttr(node);
    }

    public void addAttrs(List<AttrNode> list) {
        root.addAttrs(list);
    }

    public void addChild(TextNode node) {
        root.addChild(node);
    }

    public void addChilds(List<TextNode> nodes) {
        root.addChilds(nodes);
    }



}
