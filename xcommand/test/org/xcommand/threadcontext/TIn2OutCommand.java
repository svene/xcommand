package org.xcommand.threadcontext;

import org.xcommand.core.IImplicitXCommand;

public class TIn2OutCommand implements IImplicitXCommand
{
	public void execute()
	{
		TIn2OutCV.setOutput(TIn2OutCV.getInput());
	}
}
