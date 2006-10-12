package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;
import org.xcommand.core.NopCommand;
import org.xcommand.misc.statemachine.IState;

import java.util.Map;

public class Transition implements IXCommand
{

// --- Access ---

	public String getName()
	{
		return name;
	}

	public IState getFromState()
	{
		return fromState;
	}

	public IState getToState()
	{
		return toState;
	}

	public IXCommand getEntryCondition()
	{
		return entryCondition;
	}

	public IXCommand getEventDispatcher()
	{
		return eventDispatcher;
	}

// --- Setting ---

	public void setName(String aName)
	{
		name = aName;
	}

	public void setFromState(IState aFromState)
	{
		fromState = aFromState;
	}

	public void setToState(IState aToState)
	{
		toState = aToState;
	}

	public void setEntryCondition(IXCommand aEntryCondition)
	{
		entryCondition = aEntryCondition;
	}

	public void setEventDispatcher(IXCommand aEventDispatcher)
	{
		eventDispatcher = aEventDispatcher;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		// Exit `fromState':
		TransitionCV.setMode(aCtx, TransitionCV.ENTER);
		eventDispatcher.execute(aCtx);

		// Execute `this':
		TransitionCV.setMode(aCtx, TransitionCV.EXECUTE);
		eventDispatcher.execute(aCtx);

		// Exit `toState':
		TransitionCV.setMode(aCtx, TransitionCV.EXIT);
		eventDispatcher.execute(aCtx);
	}

// --- Implementation ---

	private String name;
	private IState fromState;
	private IState toState;
	private IXCommand entryCondition = new TrueCondition();
	private IXCommand eventDispatcher = new NopCommand();
}
