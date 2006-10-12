package org.collage.csm.transition;

import org.xcommand.core.multi.ModeBasedCommandDispatcher;
import org.xcommand.core.NopCommand;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.Transition;
import org.xcommand.misc.statemachine.TransitionCV;
import org.xcommand.misc.statemachine.IState;
import org.xcommand.misc.statemachine.FalseCondition;

import java.util.Map;
import java.util.HashMap;

public class CollageTransition extends Transition
{
	public CollageTransition(IState aFromState, IState aToState)
	{
		setFromState(aFromState);
		setToState(aToState);
		setName(aFromState.getName() + "->" + aToState.getName());
		setEntryCondition(newEntryCondition());
		setupModeCommandMap();
	}

	protected void setupModeCommandMap()
	{
		ModeBasedCommandDispatcher cdc = new ModeBasedCommandDispatcher(TransitionCV.KEY_MODE);
		Map modeCommandMap = new HashMap();
		modeCommandMap.put(TransitionCV.ENTER, newEnterCommand());
		modeCommandMap.put(TransitionCV.EXECUTE, newExecuteCommand());
		modeCommandMap.put(TransitionCV.EXIT, newExitCommand());
		cdc.setModeCommandMap(modeCommandMap);
		setEventDispatcher(cdc);
	}

	protected IXCommand newEntryCondition()
	{
		return new FalseCondition();
	}
	protected IXCommand newEnterCommand()
	{
		return new NopCommand();
	}
	protected IXCommand newExecuteCommand()
	{
		return new NopCommand();
	}
	protected IXCommand newExitCommand()
	{
		return new NopCommand();
	}
}
