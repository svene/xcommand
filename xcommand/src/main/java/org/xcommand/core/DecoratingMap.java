package org.xcommand.core;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;

public class DecoratingMap implements Map
{
	public DecoratingMap()
	{
		decoratedMap = new HashMap();
	}

	public DecoratingMap(Map aMap) {
		if (aMap == null) {
			throw new IllegalArgumentException("`aMap' must not be null");
		}
		decoratedMap = aMap;
	}

	protected Map getDecoratedMap() {
		return decoratedMap;
	}

	public void clear() {
		decoratedMap.clear();
	}

	public boolean containsKey(Object key) {
		return decoratedMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return decoratedMap.containsValue(value);
	}

	public Set entrySet() {
		return decoratedMap.entrySet();
	}

	public Object get(Object key) {
		return decoratedMap.get(key);
	}

	public boolean isEmpty() {
		return decoratedMap.isEmpty();
	}

	public Set keySet() {
		return decoratedMap.keySet();
	}

	public Object put(Object key, Object value) {
		return decoratedMap.put(key, value);
	}

	public void putAll(Map mapToCopy) {
		decoratedMap.putAll(mapToCopy);
	}

	public Object remove(Object key) {
		return decoratedMap.remove(key);
	}

	public int size() {
		return decoratedMap.size();
	}

	public Collection values() {
		return decoratedMap.values();
	}

	public boolean equals(Object object) {
		return object == this || decoratedMap.equals(object);
	}

	public int hashCode() {
		return decoratedMap.hashCode();
	}

	public String toString() {
		return decoratedMap.toString();
	}

// --- Implementation ---

	protected transient Map decoratedMap;


}
