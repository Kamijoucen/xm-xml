package com.kamijoucen.xml.build;

import com.kamijoucen.common.callback.Convert;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.utils.Utils;
import com.kamijoucen.xml.ast.AttrNode;
import com.kamijoucen.xml.ast.BaseNode;
import com.kamijoucen.xml.ast.TagBlockNode;
import com.kamijoucen.xml.ast.TextNode;

import java.util.List;

public class FomartBuilderVisitor {

    public static String visit(AttrNode node) {
        return StringUtils.joinString(node.getKey(), BUILT.ASSIGN, BUILT.STRING_DOUBLE, node.getVal(), BUILT.STRING_DOUBLE);
    }

    public static String visit(TagBlockNode node) {
        String attrsStr = StringUtils.joinSepString(" ",
                CollecUtils.convertList(node.attrs(), new Convert<AttrNode, String>() {
                    @Override
                    public String convert(AttrNode o) {
                        return visit(o);
                    }
                }));
        StringBuilder blockNodeStr = new StringBuilder();
        if (TagBlockNode.TagStartType.SINGLE.equals(node.getType())) {
            blockNodeStr.append(BUILT.TAG_START).append(node.getTagName());
            if (Utils.isNotBlankVal(attrsStr)) {
                blockNodeStr.append(" ").append(attrsStr);
            }
            blockNodeStr.append(BUILT.TAG_SINGLE_END);
        } else {
            blockNodeStr.append(BUILT.TAG_START).append(node.getTagName());
            if (Utils.isNotBlankVal(attrsStr)) {
                blockNodeStr.append(" ").append(attrsStr);
            }
            blockNodeStr.append(BUILT.TAG_END);
            List<BaseNode> childs = node.allNodes();
            for (BaseNode child : childs) {
                blockNodeStr.append(child.builder());
            }
            blockNodeStr.append(BUILT.TAG_END_START).append(node.getTagName()).append(BUILT.TAG_END);
        }
        return blockNodeStr.toString();
    }

    public static String visit(TextNode node) {
        return StringUtils.joinString(BUILT.CDATA_START, node.getText(), BUILT.CDATA_END);
    }


}