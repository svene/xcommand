package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

public abstract class ConditionObserver implements ICommand {

	public INotifier getTrueNotifier() {
		return trueNotifier;
	}

	public INotifier getFalseNotifier() {
		return falseNotifier;
	}

	private final INotifier trueNotifier = new BasicNotifier();
	private final INotifier falseNotifier = new BasicNotifier();

}
