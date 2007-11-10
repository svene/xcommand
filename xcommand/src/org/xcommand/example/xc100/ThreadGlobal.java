package org.xcommand.example.xc100;

import java.util.Map;
import java.util.HashMap;

/**
 * IGlobal implementation for Threads
 */
public class ThreadGlobal implements IGlobal
{

// --- Access ---

	public Object get(String aKey)
	{
		Map map = (Map) threadGlobals.get();
		return map.get(aKey);
	}

// --- Status report ---

	public boolean containsKey(String aKey)
	{
		return get(aKey) != null;
	}

// --- Element change ---

	public void put(String aKey, Object aValue)
	{
		Map map = (Map) threadGlobals.get();
		map.put(aKey, aValue);
	}

// --- Implementation ---

	private static ThreadLocal threadGlobals = new ThreadLocal()
	{
		protected synchronized Object initialValue()
		{
			return new HashMap();
		}
	};
}