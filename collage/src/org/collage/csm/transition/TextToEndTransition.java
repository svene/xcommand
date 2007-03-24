package org.collage.csm.transition;

import org.collage.csm.parser.CsmFlushTextCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeCV;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class TextToEndTransition extends CollageTransition
{

// --- Initialization ---

	public TextToEndTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_EOF);
	}
	protected IXCommand newExecuteCommand()
	{
		return new CsmFlushTextCommand();
	}
}
