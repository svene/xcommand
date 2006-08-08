package org.xcommand.example.xc110;

import org.xcommand.example.commands.EchoCommand;
import org.xcommand.core.ContextView;

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
		DynamicEchoContextView cv = new DynamicEchoContextView(ctx);

		// Usage:
		cv = DynamicEchoContextView.getContextView(ctx);
		cv.setMessage("Hi! I am a xcommand example. And who are you?");

		EchoCommand cmd = new EchoCommand();
		cmd.execute(ctx);

		// Removal:
		ContextView.removeContextView(ctx, DynamicEchoContextView.class);
	}
}
