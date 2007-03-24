package org.collage.csm.transition;

import org.collage.csm.parser.CsmStartTextCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeCV;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class VariableTextToTextTransition extends CollageTransition
{

// --- Initialization ---

	public VariableTextToTextTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_VAR_END_MODE);
	}
	protected IXCommand newExecuteCommand()
	{
		return new CsmStartTextCommand();
	}
}
