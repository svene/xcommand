package org.collage.csm.transition;

import org.xcommand.misc.statemachine.IState;
import org.xcommand.core.IXCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeCV;

public class StartToVariableTextTransition extends CollageTransition
{

// --- Initialization ---

	public StartToVariableTextTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_VAR_START_MODE);
	}

}
