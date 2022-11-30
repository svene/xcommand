package org.xcommand.template.jst;

import org.xcommand.core.TCP;

import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class DefaultJSTRoutines implements IJSTRoutines {
	public void ensureExistenceOfWriter() {
		var writer = (Writer) TCP.getContext().get("writer");
		if (writer == null) {
			writer = new OutputStreamWriter(System.out);
			TCP.getContext().put("writer", writer);
		}
	}

	public void $s(String aString) {
		if (aString == null) {
			throw new IllegalArgumentException("aString == null");
		}
		writeToWriter(aString);
	}

	public void $v(String aName) {
		if (aName == null) {
			throw new IllegalArgumentException("aName == null");
		}
		var value = (String) TCP.getContext().get(aName);
		if (value == null) {
			throw new IllegalArgumentException("unknown property '" + aName + "'");
		}
		writeToWriter(value);
	}

	private void writeToWriter(String aString) {
		var writer = (Writer) TCP.getContext().get("writer");
		try {
			writer.write(aString);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
