package org.xcommand.pattern.observer;

import org.xcommand.core.IXCommand;

import java.util.Map;

public interface ISubject extends IXCommand
{
	/**
	 * Register observer `aObserver' as listener to changes of thus subject.
	 */
	public void registerObserver(IXCommand aObserver);

	/**
	 * Notify all registered observers by calling their `execute' method.
	 */
	public void execute(Map aCtx);
}
