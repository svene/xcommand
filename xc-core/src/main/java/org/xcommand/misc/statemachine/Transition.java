package org.xcommand.misc.statemachine;

import org.xcommand.core.ICommand;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

public class Transition implements ICommand {

	public String getName() {
		return name;
	}

	public INotifier getPreExecuteNotifier() {
		return preExecuteNotifier;
	}

	public INotifier getExecuteNotifier() {
		return executeNotifier;
	}

	public INotifier getPostExecuteNotifier() {
		return postExecuteNotifier;
	}

	public void setName(String aName) {
		name = aName;
	}

	@Override
	public void execute() {
		preExecuteNotifier.execute();
		executeNotifier.execute();
		postExecuteNotifier.execute();
	}

	private String name;
	private final INotifier preExecuteNotifier = new BasicNotifier();
	private final INotifier executeNotifier = new BasicNotifier();
	private final INotifier postExecuteNotifier = new BasicNotifier();
}
