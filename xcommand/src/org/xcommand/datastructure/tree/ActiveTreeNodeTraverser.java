package org.xcommand.datastructure.tree;

import java.util.Stack;

/**
 * TreeNodeTraverser using push technology (like SAX XML Parsers)
 *
 * IMPORTANT: not the recommended way to traverse trees. Use
 * <code>PassiveTreeNodeTraverser</code> instead.
 * 
 * TODO: implement callback invocations
 */
public class ActiveTreeNodeTraverser
{
	public void traverse(TreeNode aRootNode)
	{
		Stack stack = new Stack();
		TreeNode te = aRootNode.getFirstChild();
		while (te != null)
		{
			if (te.hasChildren())
			{
				// Store next sibling in `stack':
				if (te.getNextSibling() != null)
				{
					stack.push(te.getNextSibling());
				}
				te = te.getFirstChild();
			}
			else
			{
				te = te.getNextSibling();
				if (te != null && !stack.isEmpty())
				{
					// Return to the parent's level
					te = (TreeNode) stack.pop();
				}
			}
		}
	}
}
