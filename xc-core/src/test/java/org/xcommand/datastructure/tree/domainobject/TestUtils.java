package org.xcommand.datastructure.tree.domainobject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {
	private void assertListContainsStrings(List<String> actualValues, String... expectedValues) {
		if (expectedValues == null) {
			throw new NullPointerException("expectedValues must not be null");
		}
		assertThat(actualValues).isNotNull();
		assertThat(expectedValues.length).isEqualTo(actualValues.size());
		Object[] avs = actualValues.toArray();
		for (int i = 0; i < expectedValues.length; i++) {
			assertThat(avs[i]).isEqualTo(expectedValues[i]);
		}
	}
}
