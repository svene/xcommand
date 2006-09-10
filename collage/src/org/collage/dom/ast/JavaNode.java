package org.collage.dom.ast;

public class JavaNode extends DomNode
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
