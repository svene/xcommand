package org.collage.csm.transition;

import org.collage.csm.CsmCompareModeCommand;
import org.collage.csm.parser.CsmAppendTextCommand;
import org.collage.parser.ParserModeCV;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class TextToTextTransition extends CollageTransition
{

// --- Initialization ---

	public TextToTextTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_TEXT);
	}

	protected IXCommand newExecuteCommand()
	{
		return new CsmAppendTextCommand();
	}
}
