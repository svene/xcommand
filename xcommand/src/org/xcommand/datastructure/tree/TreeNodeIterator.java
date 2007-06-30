package org.xcommand.datastructure.tree;

import org.xcommand.datastructure.tree.TreeNode;

import java.util.Iterator;

/**
 * Iterator implemented as an adapter to a `PassiveTreeNodeTraverser'.
 * This way clients can use a normal Iterator to traverse a tree structure. 
 */
public class TreeNodeIterator implements Iterator
{

// --- Initialization ---


	public TreeNodeIterator(TreeNode aRoot)
	{
		tnt = new PassiveTreeNodeTraverser(aRoot);
		tnt.traverseToNext();
	}

	public boolean hasNext()
	{
		return tnt.getCurrentElement() != null;
	}

	public Object next()
	{
		TreeNode te =  tnt.getCurrentElement();
		tnt.traverseToNext();
		return te;
	}

	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	/** NOTE: tnt is always one step ahead of this iterator */
	PassiveTreeNodeTraverser tnt;

}
