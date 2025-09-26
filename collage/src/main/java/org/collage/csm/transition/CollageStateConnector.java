package org.collage.csm.transition;

import org.xcommand.api.ContextViews;
import org.xcommand.core.ICommand;
import org.xcommand.misc.statemachine.IState;
import org.xcommand.misc.statemachine.Transition;

import java.util.Arrays;

public class CollageStateConnector {
	public void connect(IState aFromState, IState aToState, String aParserMode,
						ICommand[] aExecuteCommands) {
		var t = new Transition(aFromState.getName() + "->" + aToState.getName());
		ContextViews.get().defaultStateTransitionBinder.bind(aFromState, t, aToState);
		// Setup entry condition for transition:
		var pmct = new ParserModeConditionTester(aParserMode);
		// Attach transition to entry condition tester:
		pmct.getTrueNotifier().registerObserver(t);
		// Attach entry condition tester to aFromState:
		aFromState.getExecuteNotifier().registerObserver(pmct);

		// Attach commands to `t' to be executed when `t' is executed:
		if (aExecuteCommands != null) {
			Arrays.stream(aExecuteCommands).forEach(it -> t.getExecuteNotifier().registerObserver(it));
		}

	}
}
