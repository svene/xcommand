package org.xcommand.datastructure.tree;

import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;

import java.util.List;

public class TestEchoCommand implements ICommand
{
	public TestEchoCommand(String aMessage)
	{
		message = aMessage;
	}

	public void execute()
	{
		List lst = (List) TCP.getContext().get("list");
		lst.add(message);
	}

	String message;
}
