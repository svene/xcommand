package org.xcommand.threadcontext;

import java.util.Map;
import java.util.HashMap;

/**
 * Class providing a `java.util.Map' which is maintained as a `ThreadLocal' variable.
 */
public class ThreadLocalBasedMapProvider
{
	private ThreadLocalBasedMapProvider()
	{
	}
	public static Map getMap()
	{
		return (Map) threadMapHolder.get();
	}

// --- Implementation ---

	private static ThreadLocal threadMapHolder = new ThreadLocal()
	{
		protected synchronized Object initialValue()
		{
			return new HashMap();
		}
	};


// --- Implementation ---

}
