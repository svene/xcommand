package org.xcommand.misc.statemachine;

/**
 * Merge with ITransitionCV to IStateMachineCV
 */
public interface IStateCV
{
	/** @deprecated */
	public String getMode();
	public IState getState();

	/** @deprecated */
	public void setMode(String aMode);
	public void setState(IState aState);
}
