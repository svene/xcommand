package org.xcommand.example.xc100;

public interface IGlobal
{
	Object get(String aKey);
	void put(String aKey, Object aValue);

	boolean containsKey(String aKey);
}
