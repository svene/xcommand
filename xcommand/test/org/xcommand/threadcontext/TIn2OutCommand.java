package org.xcommand.threadcontext;

import org.xcommand.core.INXCommand;

public class TIn2OutCommand implements INXCommand
{
	public void execute()
	{
		TIn2OutCV.setOutput(TIn2OutCV.getInput());
	}
}
