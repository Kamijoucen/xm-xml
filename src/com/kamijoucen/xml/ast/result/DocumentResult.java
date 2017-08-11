package com.kamijoucen.xml.ast.result;


import com.kamijoucen.utils.CollecUtils;
import com.kamijoucen.utils.StringUtils;
import com.kamijoucen.xml.ast.BaseAst;
import com.kamijoucen.xml.ast.NoneAst;
import com.kamijoucen.xml.ast.TagBlockAst;

import java.util.List;

public class DocumentResult {

    private final List<TagBlockAst> roots = CollecUtils.list();

    public BaseAst child(String str) {
        BaseAst root = CollecUtils.find(roots, (o) -> StringUtils.equals(str, o.getTagName()));
        if (root == null) {
            return NoneAst.INSTANCE;
        } else {
            return root;
        }
    }

    public void format() {
        // TODO: 2017/8/10
    }

    public void addRoot(TagBlockAst ast) {
        roots.add(ast);
    }

}
