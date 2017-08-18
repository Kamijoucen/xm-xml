package com.kamijoucen.xml.ast.result;

import com.kamijoucen.core.QueryCallBack;
import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.utils.StringUtils;

import java.util.List;

public class XmlHeaderResult {
    private final List<AttrResult> attrs = CollecUtils.list();

    public String getVersion() {
        AttrResult version = CollecUtils.find(attrs, new QueryCallBack<AttrResult>() {
            @Override
            public boolean query(AttrResult o) {
                return StringUtils.equals("version", o.getKey());
            }
        });
        return version == null ? "" : version.getValue();
    }

    public String getEncoding() {
        AttrResult encoding = CollecUtils.find(attrs, new QueryCallBack<AttrResult>() {
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
