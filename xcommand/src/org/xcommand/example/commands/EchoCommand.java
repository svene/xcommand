package org.xcommand.example.commands;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class EchoCommand implements IXCommand
{
	public void execute(Map aCtx)
	{
		String message = EchoContextView.getMessage(aCtx);
		System.out.println(message);
	}
}
