package org.xcommand.example.dynproxies;

import eu.javaspecialists.books.dynamicproxies.Proxies;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MapAdaptionTest {

	private static class InheritableMapAdapter<K, V> {
		private final Map<K, V> adaptee;
		private final Map<K, V> ownMap = new HashMap<>();

		public InheritableMapAdapter(Map<K, V> adaptee) {
			this.adaptee = adaptee;
		}
		public V put(K key, V value) {
			return ownMap.put(key, value);
		}

		public V get(Object key) {
			return ownMap.containsKey(key) ? ownMap.get(key) : adaptee.get(key);
		}
	}

	@Test
	void emptyParent() {
		var adaptee = new HashMap<String, Object>();
		Map<String, Object> m = Proxies.adapt(Map.class, adaptee, new InheritableMapAdapter<>(adaptee));
		m.put("key", "Sven");
		assertThat(m.get("key")).isEqualTo("Sven");
	}
	@Test
	void populatedParent() {
		var adaptee = new HashMap<String, Object>();
		adaptee.put("key", "Parent");
		Map<String, Object> m = Proxies.adapt(Map.class, adaptee, new InheritableMapAdapter<>(adaptee));
		assertThat(m.get("key")).isEqualTo("Parent");
		m.put("key", "Sven");
		assertThat(m.get("key")).isEqualTo("Sven");
	}
}
