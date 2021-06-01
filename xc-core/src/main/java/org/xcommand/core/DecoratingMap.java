package org.xcommand.core;

import java.util.*;

public class DecoratingMap<K, V> implements Map<K, V>
{
	public DecoratingMap()
	{
		decoratedMap = new HashMap<>();
	}

	public DecoratingMap(Map<K, V> aMap) {
		Objects.nonNull(aMap);
		decoratedMap = aMap;
	}

	protected Map<K, V> getDecoratedMap() {
		return decoratedMap;
	}

	@Override
	public void clear() {
		decoratedMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return decoratedMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return decoratedMap.containsValue(value);
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return decoratedMap.entrySet();
	}

	@Override
	public V get(Object key) {
		return decoratedMap.get(key);
	}

	@Override
	public boolean isEmpty() {
		return decoratedMap.isEmpty();
	}

	@Override
	public Set<K> keySet() {
		return decoratedMap.keySet();
	}

	@Override
	public V put(K key, V value) {
		return decoratedMap.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> mapToCopy) {
		decoratedMap.putAll(mapToCopy);
	}

	@Override
	public V remove(Object key) {
		return decoratedMap.remove(key);
	}

	@Override
	public int size() {
		return decoratedMap.size();
	}

	@Override
	public Collection<V> values() {
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

	protected transient Map<K, V> decoratedMap;


}
