package org.collage.dom.ast;

public class Variable
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
