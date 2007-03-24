package org.collage.csm.transition;

import org.collage.csm.parser.CsmCreateVariableDomNodeCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeCV;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class VariableTextToVariableTextTransition extends CollageTransition
{

// --- Initialization ---

	public VariableTextToVariableTextTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_VAR_NAME_MODE);
	}

	protected IXCommand newExecuteCommand()
	{
		return new CsmCreateVariableDomNodeCommand();
	}
}
