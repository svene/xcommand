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
		DynamicEchoContextView ecv = new DynamicEchoContextView();
		DynamicEchoContextView.setContextView(ctx, ecv);

		// Usage:
		ecv = DynamicEchoContextView.getContextView(ctx);
		ecv.setMessage("Hi! I am a xcommand example. And who are you?");

		EchoCommand cmd = new EchoCommand();
		cmd.execute(ctx);

		// Removal:
		DynamicEchoContextView.removeContextView(ctx);
	}
}
