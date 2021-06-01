package org.xcommand.example.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.ICommand;

import java.lang.invoke.MethodHandles;

public class EchoCommand implements ICommand
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public EchoCommand(IEchoCV echoCV) {
		this.echoCV = echoCV;
	}

	@Override
	public void execute()
	{
		LOGGER.info(echoCV.getMessage());
	}
	private final IEchoCV echoCV;
}
