package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractBasicNotifier implements INotifier
{
	public void registerObserver(ICommand aObserver)
	{
		observers.add(aObserver);
	}

// --- Implementation ---

	protected List/*<ICommand>*/ observers = new ArrayList();
}