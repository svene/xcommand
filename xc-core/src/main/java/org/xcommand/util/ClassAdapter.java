package org.xcommand.util;

import org.xcommand.util.IObjectAdapter;
import org.xcommand.util.NestableObjectAdapter;

public class ClassAdapter extends NestableObjectAdapter
{
	public ClassAdapter(IObjectAdapter aNestedAdapter)
	{
		super(aNestedAdapter);
	}

	@Override
	public Object adaptedObject(Object aSourceObject)
	{
		Object obj = super.adaptedObject(aSourceObject);
		return obj.getClass();
	}
}
