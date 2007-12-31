package org.xcommand.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated Use class `TCP' instead
 * 
 * Thread Context
 */
public class TC extends DecoratingMap
{

	/**
	 * Private constructor to prevent instantiation from outside
	 */
	private TC()
	{
		super();
	}

// --- Access ---

	public static TC getInstance()
	{
		return instance;
	}

// --- Implementation ---

	private static ThreadLocal threadMapHolder = new ThreadLocal()
	{
		protected synchronized Object initialValue()
		{
			return new HashMap();
		}
	};
	public Map getWrappedMap()
	{
		Map map = (Map) threadMapHolder.get();
		return map;
	}

// --- Implementation ---

	private static TC instance = new TC();

}

