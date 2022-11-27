package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

public class BasicNotifier extends AbstractBasicNotifier {
	@Override
	public void execute() {
		observers.forEach(ICommand::execute);
	}

}
