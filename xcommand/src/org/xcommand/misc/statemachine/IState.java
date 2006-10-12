package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;

import java.util.List;

public interface IState extends IXCommand
{
	String getName();
	List getTransitions();

	void setName(String aName);
	void setTransition(Transition aTransition);
	void setTransitions(List aTransitions);
}
