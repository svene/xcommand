package org.xcommand.example.xc110;

import org.xcommand.example.commands.EchoContextView;
import org.xcommand.example.commands.EchoCommand;

import java.util.Map;
import java.util.HashMap;

public class Main
{

	public static void main(String[] args)
	{
		Map ctx = new HashMap();
		DynamicEchoContextView decv = new DynamicEchoContextView();
		decv.setContext(ctx);
		ctx.put("decv", decv);

		decv = (DynamicEchoContextView) ctx.get("decv");
		decv.setMessage("Hi! I am a xcommand example. And who are you?");
		EchoCommand cmd = new EchoCommand();
		cmd.execute(ctx);
	}
}
