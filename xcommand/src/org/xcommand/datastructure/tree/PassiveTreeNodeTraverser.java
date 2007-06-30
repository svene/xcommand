package org.xcommand.datastructure.tree;

import org.xcommand.datastructure.tree.TreeNode;

import java.util.Stack;

/**
 * TreeNodeTraverser using polling technology (like JIBX XML Parser)
 */
public class PassiveTreeNodeTraverser
{

// --- Initialization ---

	public PassiveTreeNodeTraverser(TreeNode aRootNode)
	{
		currentNode = aRootNode;
	}

// --- Access ---

	public TreeNode getCurrentElement()
	{
		return currentNode;
	}

// --- Processing ---

	public void traverseToNext()
	{
		if (currentNode == null) return;
		if (currentNode.hasChildren())
		{
			// Store next sibling in `stack':
			if (currentNode.getNextSibling() != null)
			{
				stack.push(currentNode.getNextSibling());
			}
			currentNode = currentNode.getFirstChild();
		}
		else
		{
			currentNode = currentNode.getNextSibling();
			if (currentNode != null && !stack.isEmpty())
			{
				// Return to the parent's level
				currentNode = (TreeNode) stack.pop();
			}
		}
	}

// --- Implementation ---

	private TreeNode currentNode;
	Stack stack = new Stack();
}
