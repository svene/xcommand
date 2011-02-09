package org.collage.csm.transition;

import org.xcommand.misc.statemachine.IState;
import org.xcommand.misc.statemachine.DefaultStateTransitionBinder;
import org.xcommand.misc.statemachine.Transition;
import org.xcommand.core.ICommand;

public class CollageStateConnector
{
	public void connect(IState aFromState, IState aToState, String aParserMode,
		ICommand[] aExecuteCommands)
	{
		Transition t = new Transition();
		t.setName(aFromState.getName() + "->" + aToState.getName());
		new DefaultStateTransitionBinder().bind(aFromState, t, aToState);
		// Setup entry condition for transition:
		ParserModeConditionTester pmct = new ParserModeConditionTester(aParserMode);
		// Attach transition to entry condition tester:
		pmct.getTrueNotifier().registerObserver(t);
		// Attach entry condition tester to aFromState:
		aFromState.getExecuteNotifier().registerObserver(pmct);

		// Attach commands to `t' to be executed when `t' is executed:
		if (aExecuteCommands != null)
		{
			for (int i = 0, n = aExecuteCommands.length; i < n; i++)
			{
				t.getExecuteNotifier().registerObserver(aExecuteCommands[i]);
			}
		}

	}
}
