package org.xcommand.datastructure.tree;

import org.xcommand.util.IObjectAdapter;

class AdapterBasedHandlerKeyProvider implements IHandlerKeyProvider
{

// --- Initialization ---

	AdapterBasedHandlerKeyProvider(IObjectAdapter aObjectAdapter)
	{
		objectAdapter = aObjectAdapter;
	}

	@Override
	public Object getHandlerKey(Object aObj)
	{
		return objectAdapter.adaptedObject(aObj);
	}

// --- Implementation ---

	private final IObjectAdapter objectAdapter;

}
