package com.kamijoucen.xml.ast;

import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;

import java.util.List;

public class XmlHeaderNode {

    private final List<AttrNode> attrs = CollecUtils.list();

    public String getVersion() {
        AttrNode version = CollecUtils.find(attrs, new Query<AttrNode>() {
            @Override
            public boolean query(AttrNode o) {
                return StringUtils.equals("version", o.getKey());
            }
        });
        return version == null ? "" : version.getVal();
    }

    public String getEncoding() {
        AttrNode encoding = CollecUtils.find(attrs, new Query<AttrNode>() {
            @Override
            public boolean query(AttrNode o) {
                return StringUtils.equals("encoding", o.getKey());
            }
        });
        return encoding == null ? "" : encoding.getVal();
    }

    public void addAttr(AttrNode attr) {
        attrs.add(attr);
    }

    public List<AttrNode> getAttrs() {
        return attrs;
    }

}
