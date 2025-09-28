package org.xcommand.datastructure.tree;

public interface ITreeNodeCV {
    ITreeNode getTreeNode();

    Object getDomainObject();

    void setTreeNode(ITreeNode aTreeNode);

    void setDomainObject(Object aDomainObject);
}
