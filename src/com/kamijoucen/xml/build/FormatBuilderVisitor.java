package com.kamijoucen.xml.build;

import com.kamijoucen.common.callback.Convert;
import com.kamijoucen.common.utils.CollecUtils;
import com.kamijoucen.common.utils.StringUtils;
import com.kamijoucen.common.utils.Utils;
import com.kamijoucen.xml.element.AttrNode;
import com.kamijoucen.xml.element.BaseNode;
import com.kamijoucen.xml.element.TagBlockNode;
import com.kamijoucen.xml.element.TextNode;

import java.util.List;


public class FormatBuilderVisitor implements Visitor {

    private int curDepth;

    public FormatBuilderVisitor(int depth) {
        this.curDepth = depth;
    }

    @Override
    public String visit(AttrNode node) {
        return StringUtils.joinString(node.getKey(), BUILT.ASSIGN, BUILT.STRING_DOUBLE, node.getVal(), BUILT.STRING_DOUBLE);
    }

    @Override
    public String visit(TagBlockNode node) {
        addDepth();
        String attrsStr = StringUtils.joinSepString(BUILT.SPACE,
                CollecUtils.convertList(node.attrs(), new Convert<AttrNode, String>() {
                    @Override
                    public String convert(AttrNode o) {
                        return o.builder(FormatBuilderVisitor.this);
                    }
                }));
        StringBuilder blockNodeStr = new StringBuilder();
        appendFmtSpace(blockNodeStr);
        if (TagBlockNode.TagStartType.SINGLE.equals(node.getType())) {

            blockNodeStr.append(BUILT.TAG_START).append(node.getTagName());
            if (Utils.isNotBlankVal(attrsStr)) {
                blockNodeStr.append(BUILT.SPACE).append(attrsStr);
            }
            blockNodeStr.append(BUILT.TAG_SINGLE_END).append(BUILT.LINE_SEPARATOR);
        } else {
            blockNodeStr.append(BUILT.TAG_START).append(node.getTagName());
            if (Utils.isNotBlankVal(attrsStr)) {
                blockNodeStr.append(BUILT.SPACE).append(attrsStr);
            }
            blockNodeStr.append(BUILT.TAG_END);
            if (!CollecUtils.isEmptyCollection(node.allNodes())) {
                blockNodeStr.append(BUILT.LINE_SEPARATOR);
                List<BaseNode> childs = node.allNodes();
                for (BaseNode child : childs) {
                    blockNodeStr.append(child.builder(this));
                }
                appendFmtSpace(blockNodeStr);
            }
            blockNodeStr.append(BUILT.TAG_END_START).append(node.getTagName()).append(BUILT.TAG_END).append(BUILT.LINE_SEPARATOR);
        }
        subDepth();
        return blockNodeStr.toString();
    }

    @Override
    public String visit(TextNode node) {
        addDepth();
        StringBuilder textStr = new StringBuilder();
        appendFmtSpace(textStr);
        textStr.append(BUILT.CDATA_START).append(node.getText()).append(BUILT.CDATA_END).append(BUILT.LINE_SEPARATOR);
        subDepth();
        return textStr.toString();
    }


    private void appendFmtSpace(StringBuilder builder) {
        for (int i = 0; i < curDepth; ++i) {
            builder.append(BUILT.FMT_SPACE);
        }
    }

    public int getCurDepth() {
        return curDepth;
    }

    private void addDepth() {
        this.curDepth += 1;
    }

    private void subDepth() {
        this.curDepth -= 1;
    }

}
