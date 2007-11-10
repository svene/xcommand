package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;
import org.xcommand.pattern.observer.StoppableNotifier;
import org.xcommand.pattern.observer.ISubject;

public interface IState extends IXCommand
{
	String getName();

	void setName(String aName);

	public ISubject getExitStateNotifier();
	public ISubject getExecuteStateNotifier();
	public ISubject getEnterStateNotifier();

	public StoppableNotifier getExecuteNotifier();
}
