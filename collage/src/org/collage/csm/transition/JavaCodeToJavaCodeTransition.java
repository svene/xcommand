package org.collage.csm.transition;

import org.collage.csm.parser.CsmAppendJavaCodeCommand;
import org.collage.csm.CsmCompareModeCommand;
import org.collage.parser.ParserModeContextView;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.IState;

public class JavaCodeToJavaCodeTransition extends CollageTransition
{

// --- Initialization ---

	public JavaCodeToJavaCodeTransition(IState aFromState, IState aToState)
	{
		super(aFromState, aToState);
	}

	protected IXCommand newEntryCondition()
	{
		return new CsmCompareModeCommand(ParserModeContextView.KEY_JAVA_CODE_MODE);
	}

	protected IXCommand newExecuteCommand()
	{
		return new CsmAppendJavaCodeCommand();
	}
}
