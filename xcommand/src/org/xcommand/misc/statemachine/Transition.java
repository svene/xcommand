package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;
import org.xcommand.pattern.observer.ISubject;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.Map;

public class Transition implements IXCommand
{

// --- Access ---

	public String getName()
	{
		return name;
	}

	public ISubject getPreExecuteNotifier()
	{
		return preExecuteNotifier;
	}

	public ISubject getExecuteNotifier()
	{
		return executeNotifier;
	}

	public ISubject getPostExecuteNotifier()
	{
		return postExecuteNotifier;
	}

// --- Setting ---

	public void setName(String aName)
	{
		name = aName;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		preExecuteNotifier.execute(aCtx);
		executeNotifier.execute(aCtx);
		postExecuteNotifier.execute(aCtx);
	}

// --- Implementation ---

	private String name;
	private ISubject preExecuteNotifier = new SubjectImpl();
	private ISubject executeNotifier = new SubjectImpl();
	private ISubject postExecuteNotifier = new SubjectImpl();
}
