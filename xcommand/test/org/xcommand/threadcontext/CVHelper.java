package org.xcommand.threadcontext;

/** ContextView Helper*/
public class CVHelper
{
	public static Object get(Object aKey)
	{
		return TCP.getContext().get(aKey);
	}
	public static void set(Object aKey, Object aValue)
	{
		TCP.getContext().put(aKey, aValue);
	}
}
