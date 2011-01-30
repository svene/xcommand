package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

public class BasicNotifier extends AbstractBasicNotifier
{

	public void execute()
	{

		for (int i = 0, n = observers.size(); i < n; i++)
		{
			ICommand observer = (ICommand) observers.get(i);
			observer.execute();
		}
	}

}