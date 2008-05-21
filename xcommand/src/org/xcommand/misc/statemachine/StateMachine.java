package org.xcommand.misc.statemachine;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;

public class StateMachine implements ICommand
{

// --- Processing ---

	/**
	 * Execute state according to `aCtx'
	 */
	public void execute()
	{
		if (stateCV.getState() == null) throw new IllegalStateException("StateCV.getState(aCtx) == null");
		stateCV.getState().execute();
	}

	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IStateCV stateCV = (IStateCV) dbp.getBeanForInterface(IStateCV.class);
}
