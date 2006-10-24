package org.collage.csm.transition;

import org.xcommand.misc.statemachine.IState;
import org.xcommand.core.IXCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.csm.parser.CsmStartTextCommand;
import org.collage.parser.ParserModeContextView;

public class JavaEolChkToTextIfEolTransition extends CollageTransition
{

// --- Initialization ---

	public JavaEolChkToTextIfEolTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeContextView.KEY_EOL);
	}
	protected IXCommand newExecuteCommand()
	{
		return new CsmStartTextCommand();
	}
}
