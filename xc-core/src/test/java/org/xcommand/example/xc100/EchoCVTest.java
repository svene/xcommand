package org.xcommand.example.xc100;

import org.junit.jupiter.api.Test;
import org.xcommand.api.ContextViews;
import org.xcommand.core.*;
import org.xcommand.example.commands.IEchoCV;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EchoCVTest {
	@Test
	void contextInThread() {
		String message = "Hi! I am a xcommand example. And who are you?";
		ContextViews.get().echoCV.setMessage(message);
		assertThat(ContextViews.get().echoCV.getMessage()).isEqualTo(message);
	}

	@Test
	void explicitContext() {
		String message = "Hi! I am a xcommand example. And who are you?";
		Map<String, Object> ctx = new HashMap<>();

		DynaBeanOptions dynaBeanOptions = DynaBeanOptionsBuilder.builder()
			.contextProvider(() -> ctx)
			.dynaBeanKeyProvider(DynaBeanKeyProviders.ClassAndMethodKeyProvider)
			.build();

		IDynaBeanProvider dbp = DynaBeanProvider.fromOptions(dynaBeanOptions);

		IEchoCV echoCV = dbp.newBeanForInterface(IEchoCV.class);

		echoCV.setMessage(message);
		assertThat(echoCV.getMessage()).isEqualTo(message);
		assertThat(ctx).isEqualTo(Map.of("org.xcommand.example.commands.IEchoCV.Message", message));
	}
}
