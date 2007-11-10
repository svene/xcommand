package org.xcommand.pattern.observer;

import org.xcommand.core.IXCommand;

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractSubject implements ISubject
{
	public void registerObserver(IXCommand aObserver)
	{
		observers.add(aObserver);
	}

// --- Implementation ---

	protected List/*<IXCommand>*/ observers = new ArrayList();
}
