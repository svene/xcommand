package org.xcommand.pattern.observer;

import org.xcommand.core.IXCommand;

public abstract class ConditionObserver implements IXCommand
{

// --- Access ---

	public ISubject getTrueNotifier()
	{
		return trueNotifier;
	}

	public ISubject getFalseNotifier()
	{
		return falseNotifier;
	}

// --- Implementation ---

	private ISubject trueNotifier = new SubjectImpl();
	private ISubject falseNotifier = new SubjectImpl();

}
