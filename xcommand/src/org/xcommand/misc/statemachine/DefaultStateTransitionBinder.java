package org.xcommand.misc.statemachine;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;

public class DefaultStateTransitionBinder implements IStateTransitionBinder
{

	public void bind(IState aFromState, Transition aTransition, final IState aToState)
	{
		aTransition.getPreExecuteNotifier().registerObserver(aFromState.getExitStateNotifier());
		aTransition.getPostExecuteNotifier().registerObserver(aToState.getEnterStateNotifier());
		aTransition.getPostExecuteNotifier().registerObserver(aToState.getExecuteStateNotifier());

		aTransition.getPostExecuteNotifier().registerObserver(new ICommand()
		{
			public void execute()
			{
				stateCV.setState(aToState);
			}
		});

		aTransition.getPostExecuteNotifier().registerObserver(aFromState.getExecuteNotifier().getStopCommand());

	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IStateCV stateCV = (IStateCV) dbp.getBeanForInterface(IStateCV.class);
}
