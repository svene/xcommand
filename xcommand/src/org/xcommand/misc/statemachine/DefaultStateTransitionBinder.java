package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;

import java.util.Map;

public class DefaultStateTransitionBinder implements IStateTransitionBinder
{

	public void bind(IState aFromState, Transition aTransition, final IState aToState)
	{
		aTransition.getPreExecuteNotifier().registerObserver(aFromState.getExitStateNotifier());
		aTransition.getPostExecuteNotifier().registerObserver(aToState.getEnterStateNotifier());
		aTransition.getPostExecuteNotifier().registerObserver(aToState.getExecuteStateNotifier());

		aTransition.getPostExecuteNotifier().registerObserver(new IXCommand()
		{
			public void execute(Map aCtx)
			{
				StateCV.setState(aCtx, aToState);
			}
		});

		aTransition.getPostExecuteNotifier().registerObserver(aFromState.getExecuteNotifier().getStopCommand());

	}

}
