package org.xcommand.misc.statemachine;

import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.pattern.observer.StoppableNotifier;

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
	}

// --- Access ---

	public String getName()
	{
		return name;
	}

	public INotifier getExitStateNotifier()
	{
		return exitStateNotifier;
	}

	public INotifier getExecuteStateNotifier()
	{
		return executeStateNotifier;
	}

	public INotifier getEnterStateNotifier()
	{
		return enterStateNotifier;
	}

	public StoppableNotifier getExecuteNotifier()
	{
		return executeNotifier;
	}

// --- Setting ---

	public void setName(String aName)
	{
		name = aName;
	}

// --- Processing ---

	public void execute()
	{
		getExecuteNotifier().execute();
	}

// --- Implementation ---

	private String name;

	private final INotifier exitStateNotifier = new BasicNotifier();
	private final INotifier executeStateNotifier = new BasicNotifier();
	private final INotifier enterStateNotifier = new BasicNotifier();

	private final StoppableNotifier executeNotifier = new StoppableNotifier();

	{
		// If no transition is currently able to execute, let state execute:
		executeNotifier.getNoStopRequestedNotifier().registerObserver(getExecuteStateNotifier());
	}
}
