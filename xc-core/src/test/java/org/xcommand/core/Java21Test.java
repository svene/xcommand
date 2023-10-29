package org.xcommand.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Java21Test {

	@Test
	void java21StringTemplateTest() {
		String name = "Bart";
		assertThat(STR."my name is \{name}").isEqualTo("my name is Bart");
		assertThat(STR."""
			my name
			is \{name}
			""").isEqualTo("""
			my name
			is Bart
			""");
	}
}
