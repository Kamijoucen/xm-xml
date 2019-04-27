package com.kamijoucen.xml.element;

import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.xml.build.BuilderVisitor;
import com.kamijoucen.xml.token.TokenLocation;

import java.util.List;

public class XmlHeaderNode implements BaseNode {

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

    @Override
    public String toFormatString() {
        return null;
    }

    @Override
    public String builder(BuilderVisitor visitor) {
        return null;
    }

    @Override
    public TokenLocation getTokenLocation() {
        return null;
    }
}
