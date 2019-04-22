package com.kamijoucen.xml.ast;

import com.kamijoucen.common.callback.Query;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;

import java.util.List;

public class XmlHeaderAst {

    private final List<AttrAst> attrs = CollecUtils.list();

    public String getVersion() {
        AttrAst version = CollecUtils.find(attrs, new Query<AttrAst>() {
            @Override
            public boolean query(AttrAst o) {
                return StringUtils.equals("version", o.getKey());
            }
        });
        return version == null ? "" : version.getVal();
    }

    public String getEncoding() {
        AttrAst encoding = CollecUtils.find(attrs, new Query<AttrAst>() {
            @Override
            public boolean query(AttrAst o) {
                return StringUtils.equals("encoding", o.getKey());
            }
        });
        return encoding == null ? "" : encoding.getVal();
    }

    public void addAttr(AttrAst attr) {
        attrs.add(attr);
    }

    public List<AttrAst> getAttrs() {
        return attrs;
    }

}
