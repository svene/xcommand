package org.collage.csm.transition;

import org.collage.csm.parser.CsmStartJavaCommand;
import org.collage.csm.parser.CsmFlushTextCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeCV;
import org.xcommand.core.IXCommand;
import org.xcommand.core.ListCommand;
import org.xcommand.misc.statemachine.IState;

public class TextToJavaCodeTransition extends CollageTransition
{

// --- Initialization ---

	public TextToJavaCodeTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_JAVA_START_MODE);
	}

	protected IXCommand newExecuteCommand()
	{
		ListCommand result = new ListCommand();
		result.getCommands().add(new CsmFlushTextCommand());
		result.getCommands().add(new CsmStartJavaCommand());
		return result;
	}
}
