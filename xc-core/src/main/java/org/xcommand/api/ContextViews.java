package org.xcommand.api;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.example.commands.IEchoCV;
import org.xcommand.misc.statemachine.DefaultStateTransitionBinder;
import org.xcommand.misc.statemachine.IStateCV;
import org.xcommand.misc.statemachine.IStateTransitionBinder;

public class ContextViews {
	private static final ContextViews instance = new ContextViews();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IStateCV stateCV = dbp.newBeanForInterface(IStateCV.class);
	public final IEchoCV echoCV = dbp.newBeanForInterface(IEchoCV.class);

	public final IStateTransitionBinder defaultStateTransitionBinder = DefaultStateTransitionBinder.create(stateCV)::bind;

	public static ContextViews get() {
		return instance;
	}
	private ContextViews() {
	}
}
