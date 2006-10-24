package org.collage.csm.transition;

import org.xcommand.misc.statemachine.IState;
import org.xcommand.core.IXCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.csm.parser.CsmFlushTextCommand;
import org.collage.csm.parser.CsmStartJavaCommand;
import org.collage.parser.ParserModeContextView;

public class StartToJavaCodeTransition extends CollageTransition
{

// --- Initialization ---

	public StartToJavaCodeTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeContextView.KEY_JAVA_START_MODE);
	}

	protected IXCommand newExecuteCommand()
	{
		return new CsmStartJavaCommand();
	}
}
