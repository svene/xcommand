package org.xcommand.datastructure.tree;

import org.xcommand.util.IObjectAdapter;

class AdapterBasedHandlerKeyProvider implements IHandlerKeyProvider
{

// --- Initialization ---

	public AdapterBasedHandlerKeyProvider(IObjectAdapter aObjectAdapter)
	{
		objectAdapter = aObjectAdapter;
	}

	public Object getHandlerKey(Object aObj)
	{
		return objectAdapter.adaptedObject(aObj);
	}

// --- Implementation ---

	private IObjectAdapter objectAdapter;

}
