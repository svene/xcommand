package org.xcommand.example.commands;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class EchoCommand implements ICommand
{
	public void execute()
	{
		String message = echoCV.getMessage();
		System.out.println(message);
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IEchoCV echoCV = (IEchoCV) dbp.newBeanForInterface(IEchoCV.class);
}
