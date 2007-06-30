package org.collage.dom;

import org.xcommand.datastructure.tree.ITreeNode;

import java.util.Map;

public class DomCV
{

// --- Access ---

	//!!!public static IDomNode getRootNode(Map aCtx)
	public static ITreeNode getRootNode(Map aCtx)
	{
		//!!!return (IDomNode) aCtx.get(KEY_ROOT_NODE);
		return (ITreeNode) aCtx.get(KEY_ROOT_NODE);
	}

// --- Setting ---

	//!!!public static void setRootNode(Map aCtx, IDomNode aRootNode)
	public static void setRootNode(Map aCtx, ITreeNode aRootNode)
	{
		aCtx.put(KEY_ROOT_NODE, aRootNode);
	}

// --- Implementation ---

	public static final String NS = "org.collage.dom.DomCV.";
	public static final String KEY_ROOT_NODE = NS + "ROOT_NODE";

}
