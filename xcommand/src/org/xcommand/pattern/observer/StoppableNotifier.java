package org.xcommand.pattern.observer;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class StoppableNotifier extends AbstractSubject
{

// --- Access ---

	public IXCommand getStopCommand()
	{
		return stopCommand;
	}

	public ISubject getNoStopRequestedNotifier()
	{
		return noStopRequestedNotifier;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{

		for (int i = 0, n = observers.size(); !stopNotifying && i < n; i++)
		{
			IXCommand observer = (IXCommand) observers.get(i);
			observer.execute(aCtx);
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

	private IXCommand stopCommand = new IXCommand()
	{
		public void execute(Map aCtx)
		{
			stopNotifying = true;
		}
	};

	private ISubject noStopRequestedNotifier = new SubjectImpl();
}
