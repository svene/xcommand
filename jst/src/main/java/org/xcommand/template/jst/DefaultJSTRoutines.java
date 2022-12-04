package org.xcommand.template.jst;

import org.jooq.lambda.Sneaky;
import org.xcommand.core.TCP;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Objects;

public class DefaultJSTRoutines implements IJSTRoutines {
	public void ensureExistenceOfWriter() {
		TCP.getContext().computeIfAbsent("writer", s -> new OutputStreamWriter(System.out));
	}

	public void $s(String aString) {
		Objects.requireNonNull(aString, "aString");
		writeToWriter(aString);
	}

	public void $v(String aName) {
		Objects.requireNonNull(aName, "aName");
		var value = (String) TCP.getContext().get(aName);
		Objects.requireNonNull(value, "unknown property '" + aName + "'");
		writeToWriter(value);
	}

	private void writeToWriter(String aString) {
		Sneaky.runnable(() -> ((Writer) TCP.getContext().get("writer")).write(aString)).run();
	}
}
