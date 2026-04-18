package org.xcommand.datastructure.tree;

import org.xcommand.core.TCP;

public interface ITreeNodeCV {
    ITreeNode getTreeNode();

    Object getDomainObject();

    void setTreeNode(ITreeNode aTreeNode);

    void setDomainObject(Object aDomainObject);

    String NS = ITreeNodeCV.class.getName() + ".";
    String KEY_TREE_NODE = NS + "TreeNode";

    static boolean hasTreeNode() {
        return TCP.getContext().containsKey(KEY_TREE_NODE);
    }
}
