package org.xcommand.core;

import java.util.Map;

/**
 * IXCommands doing nothing (Nop: no operation)
 */
public class NopCommand implements IXCommand
{
	public final void execute(Map aCtx)
	{
	}
}
