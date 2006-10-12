package org.collage.csm.state;

import org.xcommand.misc.statemachine.State;
import org.xcommand.core.IXCommand;
import org.collage.dom.creationhandler.RootNodeCreationHandler;

public class StartState extends State
{

// --- Initialization ---

	public StartState()
	{
		super("Start");
	}

	protected IXCommand newExitCommand()
	{
		return new RootNodeCreationHandler();
	}
}
