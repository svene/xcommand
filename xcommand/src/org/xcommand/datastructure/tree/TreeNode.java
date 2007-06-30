package org.xcommand.datastructure.tree;

import java.util.List;
import java.util.ArrayList;

/**
 * Class representing a node in a tree structure. Besides the 'infrastructural'
 * functionality it contains the property 'DomainObject' which makes it possible
 * to attach DomainObjects to the TreeNode.
 */
public class TreeNode
{

// --- Status report ---

	public boolean hasChildren()
	{
		return children.size() > 0;
	}

// --- Access ---

	public List /* <TreeNode> */ getChildren()
	{
		return children;
	}

	public TreeNode getFirstChild()
	{
		return (TreeNode) (children.size() == 0 ? null : children.get(0));
	}

	public TreeNode getNextSibling()
	{
		return nextSibling;
	}

	public Object getDomainObject()
	{
		return domainObject;
	}

// --- Setting ---

	public void setNextSibling(TreeNode aNextSibling)
	{
		nextSibling = aNextSibling;
	}

	public void setDomainObject(Object aDomainObject)
	{
		domainObject = aDomainObject;
	}

// --- Implementation ---

	private List children = new ArrayList();
	private TreeNode nextSibling;
	private Object domainObject;
}
