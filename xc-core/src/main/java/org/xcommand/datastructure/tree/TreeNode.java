package org.xcommand.datastructure.tree;

import java.util.List;
import java.util.ArrayList;

/**
 * Representation of a node in a tree structure. Besides the 'infrastructural'
 * functionality it contains the property 'DomainObject' which makes it possible
 * to attach DomainObjects to the TreeNode.
 */
public class TreeNode implements ITreeNode
{

// --- Status report ---

	public boolean hasChildren()
	{
		return children.size() > 0;
	}

// --- Access ---

	public List /* <ITreeNode> */ getChildren()
	{
		return children;
	}

	public Object getDomainObject()
	{
		return domainObject == null ? this : domainObject;
	}

// --- Setting ---

	public void setDomainObject(Object aDomainObject)
	{
		domainObject = aDomainObject;
	}

// --- Implementation ---

	private List children = new ArrayList();
	private Object domainObject;
}