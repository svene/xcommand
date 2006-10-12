package org.collage.csm.transition;

import org.collage.csm.parser.CsmFlushTextCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeContextView;
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
		return new CsmCompareModeCommand(ParserModeContextView.KEY_EOF);
	}
	protected IXCommand newEnterCommand()
	{
		return new CsmFlushTextCommand();
	}
}
