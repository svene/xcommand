package org.xcommand.misc.statemachine;

public interface IStateTransitionBinder
{
	void bind(IState aFromState, Transition aTransition, final IState aToState);
	void bindOrg(IState aFromState, Transition aTransition, IState aToState);
}
