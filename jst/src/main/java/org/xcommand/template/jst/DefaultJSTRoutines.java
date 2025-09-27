package org.xcommand.template.jst;

import org.jooq.lambda.Sneaky;
import org.xcommand.core.TCP;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class DefaultJSTRoutines implements IJSTRoutines {
	public void ensureExistenceOfWriter() {
		TCP.getContext().computeIfAbsent("writer", s -> new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
	}

	@Override
	public void $s(String aString) {
		Objects.requireNonNull(aString, "aString");
		writeToWriter(aString);
	}

	@Override
	public void $v(String aName) {
		Objects.requireNonNull(aName, "aName");
		var value = (String) TCP.getContext().get(aName);
		Objects.requireNonNull(value, "unknown property '" + aName + "'");
		writeToWriter(value);
	}

	private void writeToWriter(String aString) {
		if (TCP.getContext().get("writer") instanceof Writer w) {
			Sneaky.runnable(() -> w.write(aString)).run();
		}
	}
}
