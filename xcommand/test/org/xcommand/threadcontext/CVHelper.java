package org.xcommand.threadcontext;

import org.xcommand.core.TCP;

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
