package org.xcommand.misc.statemachine;

/**
 * Merge with ITransitionCV to IStateMachineCV
 */
public interface IStateCV
{
	/** @deprecated */
	String getMode();
	IState getState();

	/** @deprecated */
	void setMode(String aMode);
	void setState(IState aState);
}
