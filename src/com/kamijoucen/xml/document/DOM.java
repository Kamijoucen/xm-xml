package com.kamijoucen.xml.document;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.xml.document.dom.Attr;
import com.kamijoucen.xml.document.dom.Block;
import com.kamijoucen.xml.document.dom.SingleBlock;
import com.kamijoucen.xml.document.dom.Text;

import java.util.List;
import java.util.Map;

final public class DOM {

    private DOM() {}

    public static Block node(String name) {
        return new Block(name);
    }

    public static SingleBlock single(String name) {
        return new SingleBlock(name);
    }

    public static Text text(String text) {
        return new Text(text);
    }

    public static Text text(StringBuilder text) {
        return new Text(text.toString());
    }

    public static Attr attr(String key, String val) {
        return new Attr(key, val);
    }

    public static List<Attr> attrs(Map<String, String> kvs) {
        List<Attr> attrs = CollecUtils.list();
        for (Map.Entry<String, String> en : kvs.entrySet()) {
            attrs.add(DOM.attr(en.getKey(), en.getValue()));
        }
        return attrs;
    }
}
