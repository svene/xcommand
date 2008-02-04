package org.xcommand.core;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadContext Provider
 */
public class TCP
{
	private TCP()
	{
	}
	public static Map getContext()
	{
		return (Map) getMap().get(CONTEXT);
	}
	public static void setContext(Map aCtx)
	{
		getMap().put(CONTEXT, aCtx);
	}

// --- Implementation ---

	private static Map getMap()
	{
		return (Map) threadMapHolder.get();
	}

	private static ThreadLocal threadMapHolder = new ThreadLocal()
	{
		protected synchronized Object initialValue()
		{
			HashMap map = new HashMap();
			map.put(CONTEXT, new HashMap());
			return map;
		}
	};

	private static final String CONTEXT = "ctx";
}
