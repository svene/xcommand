package org.xcommand.core;

import java.util.HashMap;

/**
 * Executes 'command' inside a newly pushed context
 */
public record TCPQuery<R>(ResultCommand<R> command) implements ResultCommand<R> {
	@Override
	public final R execute() {
		TCP.pushContext(new HashMap<>());
		R result = command.execute();
		TCP.popContext();
		return result;
	}
}
