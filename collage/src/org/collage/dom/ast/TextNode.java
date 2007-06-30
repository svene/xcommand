package org.collage.dom.ast;

import org.xcommand.datastructure.tree.TreeNode;

public class TextNode extends TreeNode
{

// --- Access ---

	public String getValue()
	{
		return value;
	}

// --- Setting ---

	public void setValue(String aValue)
	{
		value = aValue;
	}

// --- Implementation ---

	private String value;
}
