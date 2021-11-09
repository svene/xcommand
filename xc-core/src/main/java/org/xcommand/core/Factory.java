package org.xcommand.core;

import eu.javaspecialists.books.dynamicproxies.Proxies;

import java.util.HashMap;
import java.util.Map;

public class Factory {

	public static class InheritableMapAdapter<K, V> {
		private final Map<K, V> parentMap;
		private final Map<K, V> childMap = new HashMap<>();

		public InheritableMapAdapter(Map<K, V> parentMap) {
			this.parentMap = parentMap;
		}
		public V put(K key, V value) {
			return childMap.put(key, value);
		}

		public V get(Object key) {
			return childMap.containsKey(key) ? childMap.get(key) : parentMap.get(key);
		}
	}

	public static <K, V> Map<K, V> newInheritableMap(Map<K, V> parentMap) {
		return Proxies.adapt(Map.class, parentMap, new InheritableMapAdapter<>(parentMap));
	}

}
