package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

public class BasicNotifier extends AbstractBasicNotifier
{

	@Override
	public void execute()
	{

		for (Object o : observers) {
			ICommand observer = (ICommand) o;
			observer.execute();
		}
	}

}
