package org.xcommand.datastructure.tree.domainobject;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestUtils {
	private void assertListContainsStrings(List<String> actualValues, String... expectedValues) {
		if (expectedValues == null) throw new NullPointerException("expectedValues must not be null");
		assertNotNull(actualValues);
		assertEquals(actualValues.size(), expectedValues.length);
		final Object[] avs = actualValues.toArray();
		for (int i = 0; i < expectedValues.length; i++) {
			assertEquals(expectedValues[i], avs[i]);
		}
	}
}
