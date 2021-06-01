package org.xcommand.example.xc100;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.example.commands.EchoCommand;
import org.xcommand.example.commands.IEchoCV;

public class Main
{

	public static void main(String[] args)
	{
		new Main().execute();
	}

	private void execute()
	{
		echoCV.setMessage("Hi! I am a xcommand example. And who are you?");
		EchoCommand cmd = new EchoCommand();
		cmd.execute();
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IEchoCV echoCV = dbp.newBeanForInterface(IEchoCV.class);
}
