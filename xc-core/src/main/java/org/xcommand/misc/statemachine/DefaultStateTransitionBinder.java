package org.xcommand.misc.statemachine;

public record DefaultStateTransitionBinder(IStateCV stateCV) {
	public static DefaultStateTransitionBinder create(IStateCV stateCV) {
		return new DefaultStateTransitionBinder(stateCV);
	}

	public void bind(IState aFromState, Transition aTransition, IState aToState) {
		aTransition.getPreExecuteNotifier().registerObserver(aFromState.getExitStateNotifier());
		aTransition.getPostExecuteNotifier().registerObserver(aToState.getEnterStateNotifier());
		aTransition.getPostExecuteNotifier().registerObserver(aToState.getExecuteStateNotifier());

		aTransition.getPostExecuteNotifier().registerObserver(() -> stateCV.setState(aToState));

		aTransition.getPostExecuteNotifier().registerObserver(aFromState.getExecuteNotifier().getStopCommand());

	}
}
