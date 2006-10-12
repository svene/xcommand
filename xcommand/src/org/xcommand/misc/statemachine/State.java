package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;
import org.xcommand.core.NopCommand;
import org.xcommand.core.multi.ModeBasedCommandDispatcher;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class State implements IState
{

// --- Initialization ---

	public State()
	{
		this(null);
	}

	public State(String aName)
	{
		name = aName;
		setupModeCommandMap();
	}

// --- Access ---

	public String getName()
	{
		return name;
	}

	public List getTransitions()
	{
		return transitions;
	}

// --- Setting ---

	public void setName(String aName)
	{
		name = aName;
	}

	public void setTransition(Transition aTransition)
	{
		transitions.add(aTransition);
	}

	public void setTransitions(List aTransitions)
	{
		transitions = aTransitions;
	}

	public void setCommand(IXCommand aEventDispatcher)
	{
		command = aEventDispatcher;
	}

// --- Processing ---

	public void execute(Map aContext)
	{
		command.execute(aContext);
	}

// --- Implementation ---

	protected void setupModeCommandMap()
	{
		ModeBasedCommandDispatcher cdc = new ModeBasedCommandDispatcher(StateCV.KEY_MODE);
		Map modeCommandMap = new HashMap();
		modeCommandMap.put(StateCV.ENTER, newEnterCommand());
		modeCommandMap.put(StateCV.EXECUTE, newExecuteCommand());
		modeCommandMap.put(StateCV.EXIT, newExitCommand());
		cdc.setModeCommandMap(modeCommandMap);
		setCommand(cdc);
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

	private String name;
	private List transitions = new ArrayList();
	private IXCommand command = new NopCommand();

}
