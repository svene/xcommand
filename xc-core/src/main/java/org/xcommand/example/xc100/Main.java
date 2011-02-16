package org.xcommand.example.xc100;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
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

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IEchoCV echoCV = (IEchoCV) dbp.newBeanForInterface(IEchoCV.class);
}