package org.xcommand.misc.statemachine;

@FunctionalInterface
public interface IStateTransitionBinder {
	void bind(IState aFromState, Transition aTransition, IState aToState);
}
