package org.xcommand.misc.statemachine;

import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class DefaultStateTransitionBinder implements IStateTransitionBinder
{

	@Override
	public void bind(IState aFromState, Transition aTransition, IState aToState)
	{
		aTransition.getPreExecuteNotifier().registerObserver(aFromState.getExitStateNotifier());
		aTransition.getPostExecuteNotifier().registerObserver(aToState.getEnterStateNotifier());
		aTransition.getPostExecuteNotifier().registerObserver(aToState.getExecuteStateNotifier());

		aTransition.getPostExecuteNotifier().registerObserver(() -> stateCV.setState(aToState));

		aTransition.getPostExecuteNotifier().registerObserver(aFromState.getExecuteNotifier().getStopCommand());

	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IStateCV stateCV = dbp.newBeanForInterface(IStateCV.class);
}
