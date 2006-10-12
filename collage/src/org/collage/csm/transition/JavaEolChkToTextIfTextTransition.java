package org.collage.csm.transition;

import org.xcommand.misc.statemachine.IState;
import org.xcommand.core.IXCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.csm.parser.CsmStartTextCommand;
import org.collage.csm.parser.CsmAppendTextCommand;
import org.collage.parser.ParserModeContextView;

public class JavaEolChkToTextIfTextTransition extends CollageTransition
{

// --- Initialization ---

	public JavaEolChkToTextIfTextTransition(IState aFromState, IState aToState)
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
