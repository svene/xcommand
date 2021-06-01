package org.xcommand.example.commands;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class EchoCommand implements ICommand
{
	@Override
	public void execute()
	{
		System.out.println(echoCV.getMessage());
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IEchoCV echoCV = dbp.newBeanForInterface(IEchoCV.class);
}
