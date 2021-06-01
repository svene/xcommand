package org.xcommand.core;

import java.util.Map;
import java.util.HashMap;

public class InheritableMap<K, V> extends DecoratingMap<K, V>
{

// --- Initialization ---

	public InheritableMap()
	{
		super(new HashMap<>());
	}

	public InheritableMap(Map<K, V> aMap)
	{
		super(aMap);
	}

// --- Access ---

	@Override
	public V get(Object aKey)
	{
		if (map.containsKey(aKey)) {
			return map.get(aKey);
		}
		return super.get(aKey);
	}

// --- Setting ---

	@Override
	public V put(K aKey, V aValue)
	{
		return map.put(aKey, aValue);
	}

// --- Implementation ---

	protected final Map<K, V> map = new HashMap<>();
}
