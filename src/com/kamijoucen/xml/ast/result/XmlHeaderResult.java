package com.kamijoucen.xml.ast.result;

import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;

import java.util.List;

public class XmlHeaderResult {
    private final List<AttrResult> attrs = CollecUtils.list();

    public String getVersion() {
        AttrResult version = CollecUtils.find(attrs, new Query<AttrResult>() {
            @Override
            public boolean query(AttrResult o) {
                return StringUtils.equals("version", o.getKey());
            }
        });
        return version == null ? "" : version.getValue();
    }

    public String getEncoding() {
        AttrResult encoding = CollecUtils.find(attrs, new Query<AttrResult>() {
            @Override
            public boolean query(AttrResult o) {
                return StringUtils.equals("encoding", o.getKey());
            }
        });
        return encoding == null ? "" : encoding.getValue();
    }

    public void addAttr(AttrResult attr) {
        attrs.add(attr);
    }

    public List<AttrResult> getAttrs() {
        return attrs;
    }
}
