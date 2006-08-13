package org.xcommand.pattern.observer;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.xcommand.core.IXCommand;

public class SubjectImpl implements ISubject
{
	public void registerObserver(IXCommand aObserver)
	{
		observers.add(aObserver);
	}

	public void execute(Map aCtx)
	{

		for (int i = 0, n = observers.size(); i < n; i++)
		{
			IXCommand observer = (IXCommand) observers.get(i);
			observer.execute(aCtx);
		}
	}

// --- Implementation ---

	private List/*<IXCommand>*/ observers = new ArrayList();

}
