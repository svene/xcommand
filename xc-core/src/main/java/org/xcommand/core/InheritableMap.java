package org.xcommand.core;

import org.xcommand.core.DecoratingMap;

import java.util.Map;
import java.util.HashMap;

public class InheritableMap extends DecoratingMap
{

// --- Initialization ---

	public InheritableMap()
	{
		super(new HashMap());
	}

	public InheritableMap(Map aMap)
	{
		super(aMap);
	}

// --- Access ---

	public Object get(Object aKey)
	{
		if (map.containsKey(aKey)) return map.get(aKey);
		return super.get(aKey);
	}

// --- Setting ---

	public Object put(Object aKey, Object aValue)
	{
		return map.put(aKey, aValue);
	}

// --- Implementation ---

	protected Map map = new HashMap();
}
