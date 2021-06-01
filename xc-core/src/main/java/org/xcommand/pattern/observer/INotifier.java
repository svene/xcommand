package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

public interface INotifier extends ICommand
{
	/**
	 * Register observer `aObserver' as listener to changes of thus subject.
	 */
	void registerObserver(ICommand aObserver);

	/**
	 * Notify all registered observers by calling their `execute' method.
	 */
	void execute();
}
