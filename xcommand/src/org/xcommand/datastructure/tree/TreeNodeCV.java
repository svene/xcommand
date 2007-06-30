package org.xcommand.datastructure.tree;

import java.util.Map;

public class TreeNodeCV
{

// --- Access ---

	public static TreeNode getTreeNode(Map aCtx)
	{
		return (TreeNode) aCtx.get(KEY_TREE_NODE);
	}

// --- Setting ---

	public static void setTreeNode(Map aCtx, TreeNode aTreeNode)
	{
		aCtx.put(KEY_TREE_NODE, aTreeNode);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.datastructure.tree.TreeNodeCV.";
	private static final String KEY_TREE_NODE = NS + "TREE_NODE";
}
