package com.kamijoucen.xml.ast.result;

import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.utils.StringUtils;

import java.util.List;

public class XmlHeaderResult {
    private final List<AttrResult> attrs = CollecUtils.list();

    public String getVersion() {
        AttrResult version = CollecUtils.find(attrs, (o) -> StringUtils.equals("version", o.getKey()));
        return version == null ? "" : version.getValue();
    }

    public String getEncoding() {
        AttrResult encoding = CollecUtils.find(attrs, (o) -> StringUtils.equals("encoding", o.getKey()));
        return encoding == null ? "" : encoding.getValue();
    }

    public void addAttr(AttrResult attr) {
        attrs.add(attr);
    }

    public List<AttrResult> getAttrs() {
        return attrs;
    }
}
