package com.kamijoucen.xml.element;

import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.build.BuilderVisitor;
import com.kamijoucen.xml.build.FormatBuilderVisitor;

import java.util.List;

public class DocumentTemplate {

    private TagBlockNode root = null;
    private List<TagBlockNode> list = CollecUtils.list();

    private DocumentTemplate() {
    }

    public static DocumentTemplate createDocument(String rootTagName) {
        Validate.notBlankVal(rootTagName);
        DocumentTemplate document = new DocumentTemplate();
        TagBlockNode node = new TagBlockNode(rootTagName);
        document.root = node;
        document.list.add(node);
        return document;
    }

    public static DocumentTemplate createDocument(DocumentResult result) {
        Validate.notNull(result);
        DocumentTemplate document = new DocumentTemplate();
        List<TagBlockNode> resultList = result.childs();
        document.root = CollecUtils.firstObj(resultList);
        document.list = resultList;
        return document;
    }

    public static DocumentTemplate createDocument(TagBlockNode node) {
        Validate.notNull(node);
        DocumentTemplate document = new DocumentTemplate();
        document.root = node;
        document.list.add(node);
        return document;
    }


    public String builder() {
        BuilderVisitor visitor = new BuilderVisitor();
        return root.builder(visitor);
    }


    public String formatBuilder() {
        FormatBuilderVisitor visitor = new FormatBuilderVisitor(-1);
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
