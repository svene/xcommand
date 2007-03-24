package org.collage.csm.transition;

import org.collage.csm.parser.CsmFlushTextCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeCV;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class TextToVariableTextTransition extends CollageTransition
{

// --- Initialization ---

	public TextToVariableTextTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_VAR_START_MODE);
	}

	protected IXCommand newExecuteCommand()
	{
		return new CsmFlushTextCommand();
	}
}
