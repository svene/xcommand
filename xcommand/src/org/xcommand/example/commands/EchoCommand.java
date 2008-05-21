package org.xcommand.example.commands;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;

public class EchoCommand implements ICommand
{
	public void execute()
	{
		String message = echoCV.getMessage();
		System.out.println(message);
	}
	private IEchoCV echoCV = (IEchoCV) new DynaBeanProvider().getBeanForInterface(IEchoCV.class);
}
