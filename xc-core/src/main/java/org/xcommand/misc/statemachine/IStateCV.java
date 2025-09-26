package org.xcommand.misc.statemachine;

/**
 * Merge with ITransitionCV to IStateMachineCV
 */
public interface IStateCV {
	@Deprecated
	String getMode();

	IState getState();

	@Deprecated
	void setMode(String aMode);

	void setState(IState aState);
}
