package org.xcommand.datastructure.tree.specifictreenode;

import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeBuilder;
import org.xcommand.datastructure.tree.specifictreenode.domain.RootTreeNode;
import org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode1;
import org.xcommand.datastructure.tree.specifictreenode.domain.TreeNode2;

public class TestDataProvider
{

// --- Access ---

	public TreeNode getRoot1()
	{
		return root1;
	}

	public ITreeNode getRoot2()
	{
		return root2;
	}

// --- Implementation ---

	private TreeNode root1;
	private ITreeNode root2;

	{
		TreeBuilder tb = new TreeBuilder();

		// Setup element structure to test:
		root1 = new RootTreeNode();

		TreeNode1 te1 = new TreeNode1();
		tb.addChild(root1, te1);

		TreeNode2 te2 = new TreeNode2();
		tb.addChild(te1, te2);

		root2 = new RootTreeNode();
		te1 = new TreeNode1();
		tb.addChild(root2, te1);

		te2 = new TreeNode2();
		tb.addChild(root2, te2);
	}

}
