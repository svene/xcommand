package org.xcommand.datastructure.tree;

import org.xcommand.util.IObjectAdapter;
import org.xcommand.util.NestableObjectAdapter;

class TreeNodeAdapter extends NestableObjectAdapter
{

// --- Initialization ---

	TreeNodeAdapter(IObjectAdapter aNestedAdapter)
	{
		super(aNestedAdapter);
	}

	public Object adaptedObject(Object aSourceObject)
	{
		return super.adaptedObject(aSourceObject);
	}
}
