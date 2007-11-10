package org.xcommand.misc.statemachine;

import org.xcommand.pattern.observer.StoppableNotifier;
import org.xcommand.pattern.observer.ISubject;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.*;

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

	public ISubject getExitStateNotifier()
	{
		return exitStateNotifier;
	}

	public ISubject getExecuteStateNotifier()
	{
		return executeStateNotifier;
	}

	public ISubject getEnterStateNotifier()
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

	public void execute(Map aCtx)
	{
		getExecuteNotifier().execute(aCtx);
	}

// --- Implementation ---

	private String name;

	private ISubject exitStateNotifier = new SubjectImpl();
	private ISubject executeStateNotifier = new SubjectImpl();
	private ISubject enterStateNotifier = new SubjectImpl();

	private StoppableNotifier executeNotifier = new StoppableNotifier();

	{
		// If no transition is currently able to execute, let state execute:
		executeNotifier.getNoStopRequestedNotifier().registerObserver(getExecuteStateNotifier());
	}
}
