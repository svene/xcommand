package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;

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

	public IXCommand getExecuteCommand()
	{
		return executeCommand;
	}

//	public IXCommand getEventDispatcher()
//	{
//		return eventDispatcher;
//	}

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

	public void setExecuteCommand(IXCommand aExecuteCommand)
	{
		executeCommand = aExecuteCommand;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		executeCommand.execute(aCtx);
	}

// --- Implementation ---

	private String name;
	private IState fromState;
	private IState toState;
	private IXCommand entryCondition = new TrueCondition();
	private IXCommand executeCommand;
}
