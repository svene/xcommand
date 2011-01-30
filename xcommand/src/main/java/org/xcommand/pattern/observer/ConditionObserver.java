package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

public abstract class ConditionObserver implements ICommand
{

// --- Access ---

	public INotifier getTrueNotifier()
	{
		return trueNotifier;
	}

	public INotifier getFalseNotifier()
	{
		return falseNotifier;
	}

// --- Implementation ---

	private INotifier trueNotifier = new BasicNotifier();
	private INotifier falseNotifier = new BasicNotifier();

}
