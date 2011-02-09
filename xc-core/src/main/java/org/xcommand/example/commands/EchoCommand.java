package org.xcommand.example.commands;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

public class EchoCommand implements ICommand
{
	public void execute()
	{
		String message = echoCV.getMessage();
		System.out.println(message);
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IEchoCV echoCV = (IEchoCV) dbp.newBeanForInterface(IEchoCV.class);
}
