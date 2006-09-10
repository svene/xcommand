package org.xcommand.example.xc100;

import org.xcommand.example.commands.EchoContextView;
import org.xcommand.example.commands.EchoCommand;

import java.util.Map;
import java.util.HashMap;

public class Main
{

	public static void main(String[] args)
	{
		Map ctx = new HashMap();
		EchoContextView.setMessage(ctx, "Hi! I am a xcommand example. And who are you?");
		EchoCommand cmd = new EchoCommand();
		cmd.execute(ctx);
	}
}
