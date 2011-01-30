package org.xcommand.util;

public class NestableObjectAdapter implements IObjectAdapter
{

// --- Initialization ---

	public NestableObjectAdapter(IObjectAdapter aNestedAdapter)
	{
		nestedAdapter = aNestedAdapter;
	}

// --- Access ---

	public Object adaptedObject(Object aSourceObject)
	{
		if (nestedAdapter == null) return aSourceObject;
		return nestedAdapter.adaptedObject(aSourceObject);
	}

// --- Implementation ---

	private IObjectAdapter nestedAdapter;
}
