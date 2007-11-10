package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class StateMachine implements IXCommand
{

// --- Processing ---

	/**
	 * Execute state according to `aCtx'
	 */
	public void execute(Map aCtx)
	{
		if (StateCV.getState(aCtx) == null) throw new IllegalStateException("StateCV.getState(aCtx) == null");
		StateCV.getState(aCtx).execute(aCtx);
	}

}
