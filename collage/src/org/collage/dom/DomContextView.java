package org.collage.dom;

import org.collage.dom.ast.IDomNode;

import java.util.Map;

public class DomContextView
{

// --- Access ---

	public static IDomNode getRootNode(Map aCtx)
	{
		return (IDomNode) aCtx.get(KEY_ROOT_NODE);
	}

// --- Setting ---

	public static void setRootNode(Map aCtx, IDomNode aRootNode)
	{
		aCtx.put(KEY_ROOT_NODE, aRootNode);
	}

// --- Implementation ---

	public static final String NS = "org.collage.dom.DomContextView.";
	public static final String KEY_ROOT_NODE = NS + "ROOT_NODE";

}
