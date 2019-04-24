//package com.kamijoucen.xml.ast.result;
//
//import com.kamijoucen.xml.ast.AttrNode;
//import com.kamijoucen.xml.ast.TagBlockNode;
//import com.kamijoucen.xml.ast.TagNode;
//import com.kamijoucen.xml.visitor.TemplateBuilderVisitor;
//
//import java.util.List;
//import java.util.Map;
//
//public class DocumentTemplate {
//
//    private TagBlockNode root;
//    private DocumentTemplate() {}
//
//    public static DocumentTemplate create(String root) {
//        DocumentTemplate templete = new DocumentTemplate();
//        templete.root = DOM.node(root);
//        return templete;
//    }
//
//    public DocumentTemplate putAttr(String key, String val) {
//        root.putAttr(DOM.attr(key, val));
//        return this;
//    }
//
//    public DocumentTemplate putAttr(AttrNode attr) {
//        root.putAttr(attr);
//        return this;
//    }
//
//    public DocumentTemplate putAttrs(List<AttrNode> attrs) {
//        root.putAttrs(attrs);
//        return this;
//    }
//
//    public DocumentTemplate putAttrs(Map<String, String> attrs) {
//        root.putAttrs(DOM.attrs(attrs));
//        return this;
//    }
//
//    public DocumentTemplate putChild(String childName) {
//        root.addChild(DOM.node(childName));
//        return this;
//    }
//
//    public DocumentTemplate putChild(TagNode node) {
//        root.addChild(node);
//        return this;
//    }
//
//    public String buildString() {
//        // todo 处理head和doctype
//        TemplateBuilderVisitor visitor = new TemplateBuilderVisitor();
//        return root.builder(visitor);
//    }
//
//    public String buildFormatString() {
//        // todo
//        return "";
//    }
//
//    public void buildFile(String path) {
//        // todo 处理head和doctype
//    }
//
//    public void buildFormatFile(String path) {
//        // todo
//    }
//
//}
