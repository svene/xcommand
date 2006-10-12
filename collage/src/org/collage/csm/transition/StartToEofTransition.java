package org.collage.csm.transition;

import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeContextView;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class StartToEofTransition extends CollageTransition
{

// --- Initialization ---

	public StartToEofTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeContextView.KEY_EOF);
	}

}
