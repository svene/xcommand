package org.xcommand.example.xc110;

import org.xcommand.example.commands.EchoCommand;

import java.util.Map;
import java.util.HashMap;

/**
 * Demonstration of a dynamic context view
 */
public class Main
{

	public static void main(String[] args)
	{
		// Setup of context:
		Map ctx = new HashMap();
		EchoContextView.setContextView(ctx, new DynamicEchoContextView());

		// Usage:
		DynamicEchoContextView decv = EchoContextView.getContextView(ctx);
		decv.setMessage("Hi! I am a xcommand example. And who are you?");

		EchoCommand cmd = new EchoCommand();
		cmd.execute(ctx);

		// Removal:
		EchoContextView.removeContextView(ctx);
	}
}
