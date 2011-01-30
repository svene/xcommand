package org.xcommand.datastructure.tree;

import org.xcommand.util.IObjectAdapter;
import org.xcommand.util.NestableObjectAdapter;

class TreeNodeAdapter extends NestableObjectAdapter
{

// --- Initialization ---

	public TreeNodeAdapter(IObjectAdapter aNestedAdapter)
	{
		super(aNestedAdapter);
	}

	public Object adaptedObject(Object aSourceObject)
	{
		return (ITreeNode) super.adaptedObject(aSourceObject);
	}
}
