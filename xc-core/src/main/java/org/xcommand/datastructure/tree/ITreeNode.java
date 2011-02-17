package org.xcommand.datastructure.tree;

import java.util.List;

public interface ITreeNode
{
	boolean hasChildren();

	List<ITreeNode> getChildren();

	Object getDomainObject();

	void setDomainObject(Object aDomainObject);
}
