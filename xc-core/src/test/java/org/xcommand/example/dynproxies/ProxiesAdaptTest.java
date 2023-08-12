package org.xcommand.example.dynproxies;

import eu.javaspecialists.books.dynamicproxies.Proxies;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProxiesAdaptTest {

	@Test
	void lenghtNotProxied() {
		String orgString = "Good morning Vietnam";
		String patchString = "Kilroy";
		CharSequence seq =
			Proxies.adapt(CharSequence.class,
				orgString,
				new Object() {
					@Override
					public String toString() {
						return patchString;
					}

					public int length() {
						return 42;
					}
				});
		assertThat(seq.toString()).isEqualTo(patchString);
		assertThat(seq.length()).isEqualTo(orgString.length());
	}

	public interface LengthProvider {
		int length();
	}

	@Test
	void lenghtProxiedCorrectly() {
		String orgString = "Good morning Vietnam";
		String patchString = "Kilroy";
		CharSequence seq =
			Proxies.adapt(CharSequence.class,
				orgString,
				new LengthProvider() {
					@Override
					public String toString() {
						return patchString;
					}

					@Override
					public int length() {
						return 42;
					}
				});
		assertThat(seq.toString()).isEqualTo(patchString);
		assertThat(seq.length()).isEqualTo(42);
	}

}
