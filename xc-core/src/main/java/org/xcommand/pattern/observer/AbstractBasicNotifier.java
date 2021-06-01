package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractBasicNotifier implements INotifier
{
	@Override
	public void registerObserver(ICommand aObserver)
	{
		observers.add(aObserver);
	}

// --- Implementation ---

	protected final List/*<ICommand>*/ observers = new ArrayList();
}
