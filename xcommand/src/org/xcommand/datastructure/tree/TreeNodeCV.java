package org.xcommand.datastructure.tree;

import java.util.Map;

public class TreeNodeCV
{

// --- Access ---

	public static ITreeNode getTreeNode(Map aCtx)
	{
		return (ITreeNode) aCtx.get(KEY_TREE_NODE);
	}

	public static Object getDomainObject(Map aCtx)
	{
		return aCtx.get(KEY_DOMAIN_OBJECT);
	}

// --- Setting ---

	public static void setTreeNode(Map aCtx, ITreeNode aTreeNode)
	{
		aCtx.put(KEY_TREE_NODE, aTreeNode);
	}

	public static void setDomainObject(Map aCtx, Object aDomainObject)
	{
		aCtx.put(KEY_DOMAIN_OBJECT, aDomainObject);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.datastructure.tree.TreeNodeCV.";
	private static final String KEY_TREE_NODE = NS + "TREE_NODE";
	private static final String KEY_DOMAIN_OBJECT = NS + "DOMAIN_OBJECT";
}
