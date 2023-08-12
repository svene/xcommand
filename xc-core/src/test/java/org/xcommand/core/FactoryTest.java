package org.xcommand.core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FactoryTest {

	@Test
	void emptyParent() {
		var parentMap = new HashMap<String, Object>();
		Map<String, Object> m = Factory.newInheritableMap(parentMap);
		m.put("key", "Sven");
		assertThat(m).containsEntry("key", "Sven");
	}

	@Test
	void populatedParent() {
		var parentMap = new HashMap<String, Object>();
		parentMap.put("key", "Parent");
		Map<String, Object> m = Factory.newInheritableMap(parentMap);
		assertThat(m).containsEntry("key", "Parent");
		m.put("key", "Sven");
		assertThat(m).containsEntry("key", "Sven");
		assertThat(parentMap).containsEntry("key", "Parent");
	}
}
