package org.xcommand.misc.statemachine;

import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

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
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IStateCV stateCV = (IStateCV) dbp.newBeanForInterface(IStateCV.class);
}
