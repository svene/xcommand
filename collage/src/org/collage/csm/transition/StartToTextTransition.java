package org.collage.csm.transition;

import org.collage.csm.parser.CsmStartTextCommand;
import org.collage.csm.parser.CsmAppendTextCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeContextView;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class StartToTextTransition extends CollageTransition
{

// --- Initialization ---

	public StartToTextTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeContextView.KEY_TEXT);
	}

	protected IXCommand newExecuteCommand()
	{
		return new CsmStartTextCommand();
	}

	protected IXCommand newExitCommand()
	{
		return new CsmAppendTextCommand();
	}
}
