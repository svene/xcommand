package org.collage.dom.ast;

import org.xcommand.datastructure.tree.TreeNode;

public class VariableNode extends TreeNode
{

// --- Access ---

	public String getVariableName()
	{
		return variableName;
	}

// --- Setting ---

	public void setVariableName(String aVariableName)
	{
		variableName = aVariableName;
	}

// --- Implementation ---

	private String variableName;
}
