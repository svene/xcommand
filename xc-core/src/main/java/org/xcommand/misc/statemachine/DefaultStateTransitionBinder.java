package org.xcommand.misc.statemachine;

import java.util.Arrays;

public record DefaultStateTransitionBinder(IStateCV stateCV) {
    public static DefaultStateTransitionBinder create(IStateCV stateCV) {
        return new DefaultStateTransitionBinder(stateCV);
    }

    public void bind(IState aFromState, Transition aTransition, IState aToState) {
        aTransition.getPreExecuteNotifier().registerObserver(aFromState.getExitStateNotifier());

        Arrays.asList(
                        aToState.getEnterStateNotifier(),
                        aToState.getExecuteStateNotifier(),
                        () -> stateCV.setState(aToState),
                        aFromState.getExecuteNotifier().getStopCommand())
                .forEach(it -> aTransition.getPostExecuteNotifier().registerObserver(it));
    }
}
