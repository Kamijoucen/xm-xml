package com.kamijoucen.xml.ast.result;

import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.validate.Validate;
import com.kamijoucen.xml.ast.*;

import java.util.List;
import java.util.Map;

final public class DOM {

    private DOM() {}

    public static TagNode node(String name) {
        Validate.notBlankVal(name);
        TagBlockNode node = new TagBlockNode(name);
        node.setTagStartType(TagStartNode.TagStartType.BLOCK);
        return node;
    }

    public static TagNode single(String name) {
        Validate.notBlankVal(name);
        TagBlockNode node = new TagBlockNode(name);
        node.setTagStartType(TagStartNode.TagStartType.SINGLE);
        return node;
    }

    public static TextNode text(String text) {
        return new TextNode(text, null);
    }

    public static TextNode text(StringBuilder text) {
        return new TextNode(text.toString(), null);
    }

    public static AttrNode attr(String key, String val) {
        return new AttrNode(key, val, null);
    }

    public static List<AttrNode> attrs(Map<String, String> kvs) {
        List<AttrNode> attrs = CollecUtils.list();
        for (Map.Entry<String, String> en : kvs.entrySet()) {
            attrs.add(DOM.attr(en.getKey(), en.getValue()));
        }
        return attrs;
    }
}
