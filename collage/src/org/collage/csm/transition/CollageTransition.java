package org.collage.csm.transition;

import org.xcommand.core.IXCommand;
import org.xcommand.core.NopCommand;
import org.xcommand.misc.statemachine.FalseCondition;
import org.xcommand.misc.statemachine.IState;
import org.xcommand.misc.statemachine.Transition;

public class CollageTransition extends Transition
{
	public CollageTransition(IState aFromState, IState aToState)
	{
		setFromState(aFromState);
		setToState(aToState);
		setName(aFromState.getName() + "->" + aToState.getName());
		setEntryCondition(newEntryCondition());
		setExecuteCommand(newExecuteCommand());
	}

	protected IXCommand newEntryCondition()
	{
		return new FalseCondition();
	}

	protected IXCommand newExecuteCommand()
	{
		return new NopCommand();
	}
}
