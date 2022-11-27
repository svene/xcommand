package org.xcommand.util;

public class NestableObjectAdapter implements IObjectAdapter {

	public NestableObjectAdapter(IObjectAdapter aNestedAdapter) {
		nestedAdapter = aNestedAdapter;
	}

	@Override
	public Object adaptedObject(Object aSourceObject) {
		return nestedAdapter == null ? aSourceObject : nestedAdapter.adaptedObject(aSourceObject);
	}

	private final IObjectAdapter nestedAdapter;
}
