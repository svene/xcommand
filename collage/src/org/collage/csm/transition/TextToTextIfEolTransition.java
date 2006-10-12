package org.collage.csm.transition;

import org.collage.csm.CsmCompareModeCommand;
import org.collage.csm.parser.CsmAppendEolCommand;
import org.collage.parser.ParserModeContextView;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class TextToTextIfEolTransition extends CollageTransition
{

// --- Initialization ---

	public TextToTextIfEolTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeContextView.KEY_EOL);
	}

	protected IXCommand newExecuteCommand()
	{
		return new CsmAppendEolCommand();
	}
}
