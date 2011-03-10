package org.xcommand.datastructure.tree;

import org.xcommand.util.IObjectAdapter;
import org.xcommand.util.NestableObjectAdapter;

class TreeNodeDomainObjectAdapter extends NestableObjectAdapter
{

// --- Initialization ---

	public TreeNodeDomainObjectAdapter(IObjectAdapter aNestedAdapter)
	{
		super(aNestedAdapter);
	}

	@Override
	public Object adaptedObject(Object aSourceObject)
	{
		ITreeNode tn = (ITreeNode) (super.adaptedObject(aSourceObject));
		return tn.getDomainObject();
	}
}
