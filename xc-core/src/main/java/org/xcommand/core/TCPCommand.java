package org.xcommand.core;

import java.util.HashMap;

/**
 * Executes 'command' inside a newly pushed context
 */
public record TCPCommand(ICommand command) implements ICommand {
	@Override
	public final void execute() {
		TCP.pushContext(new HashMap<>());
		command.execute();
		TCP.popContext();
	}
}
