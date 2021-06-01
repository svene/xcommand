package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

public class StoppableNotifier extends AbstractBasicNotifier
{

// --- Access ---

	public ICommand getStopCommand()
	{
		return stopCommand;
	}

	public INotifier getNoStopRequestedNotifier()
	{
		return noStopRequestedNotifier;
	}

// --- Processing ---

	public void execute()
	{

		for (int i = 0, n = observers.size(); !stopNotifying && i < n; i++)
		{
			ICommand observer = (ICommand) observers.get(i);
			observer.execute();
		}
		if (!stopNotifying)
		{
			// since none of the observers did send the 'stopNotifying' signal, send a notification about this fact: 
		}
		// reset `stopNotifying':
		stopNotifying = false;
	}

// --- Implementation ---

	private boolean stopNotifying = false;

	private final ICommand stopCommand = new ICommand()
	{
		public void execute()
		{
			stopNotifying = true;
		}
	};

	private final INotifier noStopRequestedNotifier = new BasicNotifier();
}
