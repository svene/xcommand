package org.xcommand.util;

public class NestableObjectAdapter implements IObjectAdapter
{

// --- Initialization ---

	public NestableObjectAdapter(IObjectAdapter aNestedAdapter)
	{
		nestedAdapter = aNestedAdapter;
	}

// --- Access ---

	@Override
	public Object adaptedObject(Object aSourceObject)
	{
		return nestedAdapter == null ? aSourceObject : nestedAdapter.adaptedObject(aSourceObject);
	}

// --- Implementation ---

	private final IObjectAdapter nestedAdapter;
}
