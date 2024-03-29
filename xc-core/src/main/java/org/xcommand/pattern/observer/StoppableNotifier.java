package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

public class StoppableNotifier extends AbstractBasicNotifier {

	public ICommand getStopCommand() {
		return stopCommand;
	}

	public INotifier getNoStopRequestedNotifier() {
		return noStopRequestedNotifier;
	}

	@Override
	public void execute() {

		for (int i = 0, n = observers.size(); !stopNotifying && i < n; i++) {
			observers.get(i).execute();
		}
		if (!stopNotifying) {
			// since none of the observers did send the 'stopNotifying' signal, send a notification about this fact: 
		}
		// reset `stopNotifying':
		stopNotifying = false;
	}

	private boolean stopNotifying;
	private final ICommand stopCommand = () -> stopNotifying = true;
	private final INotifier noStopRequestedNotifier = new BasicNotifier();
}
