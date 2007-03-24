package org.collage.csm.transition;

import org.xcommand.misc.statemachine.IState;
import org.xcommand.core.IXCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.csm.parser.CsmFlushJavaCommand;
import org.collage.parser.ParserModeCV;

public class JavaCodeToJavaEolChkTransition extends CollageTransition
{

// --- Initialization ---

	public JavaCodeToJavaEolChkTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeCV.KEY_JAVA_END_MODE);
	}

	protected IXCommand newExecuteCommand()
	{
		return new CsmFlushJavaCommand();
	}
}
