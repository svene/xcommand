package org.xcommand.api;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.example.commands.EchoCommand;
import org.xcommand.example.commands.IEchoCV;
import org.xcommand.misc.statemachine.*;

public class ContextViews {
	private static final ContextViews instance = new ContextViews();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IStateCV stateCV = dbp.newBeanForInterface(IStateCV.class);
	public final IEchoCV echoCV = dbp.newBeanForInterface(IEchoCV.class);

	public final IStateTransitionBinder defaultStateTransitionBinder = DefaultStateTransitionBinder.create(stateCV)::bind;
	public final ICommand echoCommand = new EchoCommand(echoCV);

	public static ContextViews get() {
		return instance;
	}
	private ContextViews() {
	}
}
