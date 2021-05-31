package org.xcommand.misc.statemachine;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

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

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IStateCV stateCV = dbp.newBeanForInterface(IStateCV.class);
}
