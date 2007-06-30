package org.xcommand.datastructure.tree;

/**
 * Helper class to construct Tree structures 
 */
public class TreeBuilder
{
	public void addChild(TreeNode aParent, TreeNode aChild)
	{
		int size = aParent.getChildren().size();
		if (size > 0)
		{
			// Link siblings:
			TreeNode lastChild = (TreeNode) (aParent.getChildren().get(size - 1));
			lastChild.setNextSibling(aChild);
		}
		aParent.getChildren().add(aChild);
	}
}
