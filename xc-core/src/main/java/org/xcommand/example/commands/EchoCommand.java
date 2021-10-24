package org.xcommand.example.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.ICommand;

import java.lang.invoke.MethodHandles;

public record EchoCommand(IEchoCV echoCV) implements ICommand {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public void execute() {
		LOGGER.info(echoCV.getMessage());
	}
}
