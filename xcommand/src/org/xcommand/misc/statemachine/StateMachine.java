package org.xcommand.misc.statemachine;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

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

	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IStateCV stateCV = (IStateCV) dbp.newBeanForInterface(IStateCV.class);
}
