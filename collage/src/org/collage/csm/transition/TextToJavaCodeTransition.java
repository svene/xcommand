package org.collage.csm.transition;

import org.collage.csm.parser.CsmStartJavaCommand;
import org.collage.csm.parser.CsmFlushTextCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeContextView;
import org.xcommand.core.IXCommand;
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
		return new CsmCompareModeCommand(ParserModeContextView.KEY_JAVA_START_MODE);
	}

	protected IXCommand newEnterCommand()
	{
		return new CsmFlushTextCommand();
	}

	protected IXCommand newExitCommand()
	{
		return new CsmStartJavaCommand();
	}
}
