package org.xcommand.datastructure.tree.specifictreenode;

import org.xcommand.datastructure.tree.TreeNode;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeBuilder;

public class TestDataProvider
{

// --- Access ---

	public TreeNode getRoot1()
	{
		return root1;
	}
	public ITreeNode getRoot1Child() {
		return root1.getChildren().get(0);
	}
	public ITreeNode getRoot1ChildChild() {
		return getRoot1Child().getChildren().get(0);
	}

	public ITreeNode getRoot2()
	{
		return root2;
	}

	public ITreeNode getRoot2Child1() {
		return root2.getChildren().get(0);
	}
	public ITreeNode getRoot2Child2() {
		return root2.getChildren().get(1);
	}

	public static class RootTreeNode extends TreeNode {}
	public static class TreeNode2 extends TreeNode {}
	public static class TreeNode1 extends TreeNode {}

// --- Implementation ---

	private TreeNode root1;
	private ITreeNode root2;

	{
		/*
		  root1/te1/te2

		  root2/te1
		  root2/te2
		
		 */
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
