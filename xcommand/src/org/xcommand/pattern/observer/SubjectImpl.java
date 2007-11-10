package org.xcommand.pattern.observer;

import java.util.Map;

import org.xcommand.core.IXCommand;

public class SubjectImpl extends AbstractSubject
{

	public void execute(Map aCtx)
	{

		for (int i = 0, n = observers.size(); i < n; i++)
		{
			IXCommand observer = (IXCommand) observers.get(i);
			observer.execute(aCtx);
		}
	}

}
