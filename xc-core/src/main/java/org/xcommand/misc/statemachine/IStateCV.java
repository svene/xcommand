package org.xcommand.misc.statemachine;

/**
 * Merge with ITransitionCV to IStateMachineCV
 */
public interface IStateCV {
	/**
	 * @deprecated
	 */
	@Deprecated
	String getMode();

	IState getState();

	/**
	 * @deprecated
	 */
	@Deprecated
	void setMode(String aMode);

	void setState(IState aState);
}
