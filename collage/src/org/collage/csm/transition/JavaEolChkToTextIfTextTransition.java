package org.collage.csm.transition;

import org.xcommand.misc.statemachine.IState;
import org.xcommand.core.IXCommand;
import org.xcommand.core.ListCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.csm.parser.CsmStartTextCommand;
import org.collage.csm.parser.CsmAppendTextCommand;
import org.collage.parser.ParserModeCV;

public class JavaEolChkToTextIfTextTransition extends CollageTransition
{

// --- Initialization ---

	public JavaEolChkToTextIfTextTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_TEXT);
	}

	protected IXCommand newExecuteCommand()
	{
		ListCommand result = new ListCommand();
		result.getCommands().add(new CsmStartTextCommand());
		result.getCommands().add(new CsmAppendTextCommand());
		return result;
	}
}
