package org.xcommand.example.xc100;

import org.junit.jupiter.api.Test;
import org.xcommand.api.ContextViews;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EchoCVTest {
	@Test
	void example1() {
		String message = "Hi! I am a xcommand example. And who are you?";
		ContextViews.get().echoCV.setMessage(message);
		assertThat(ContextViews.get().echoCV.getMessage()).isEqualTo(message);
	}
}
