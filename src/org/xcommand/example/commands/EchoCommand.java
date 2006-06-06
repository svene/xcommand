package org.xcommand.example.commands;

import org.xcommand.ICommand;

import java.util.Map;

public class EchoCommand implements ICommand
{

	public EchoCommand(String message)
	{
		this.message = message;
	}

// --- Setting ---

	public void setMessage(String message)
	{
		this.message = message;
	}

	public void execute(Map aContext)
	{
		System.out.println(message);
	}

	private String message;
}
