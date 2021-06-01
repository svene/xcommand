package org.xcommand.api;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.misc.statemachine.*;

public class ContextViews {
	private static final ContextViews instance = new ContextViews();
	private final IDynaBeanProvider dbp;
	private final IStateCV stateCV;
	public static ContextViews get()
	{
		return instance;
	}
	private ContextViews() {
		this.dbp = DynaBeanProvider.newThreadClassMethodInstance();
		this.stateCV = dbp.newBeanForInterface(IStateCV.class);;
	}

	public IStateTransitionBinder defaultStateTransitionBinder() {
		return DefaultStateTransitionBinder.create(stateCV)::bind;
	}
}
