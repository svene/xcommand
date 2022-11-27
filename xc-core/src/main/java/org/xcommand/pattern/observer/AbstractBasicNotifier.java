package org.xcommand.pattern.observer;

import org.xcommand.core.ICommand;

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractBasicNotifier implements INotifier {
	protected final List<ICommand> observers = new ArrayList<>();

	@Override
	public void registerObserver(ICommand aObserver) {
		observers.add(aObserver);
	}

}
