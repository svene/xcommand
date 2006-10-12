package org.collage.csm;

import org.collage.parser.ParserModeContextView;
import org.xcommand.misc.statemachine.CompareModeCommand;

public class CsmCompareModeCommand extends CompareModeCommand
{
	public CsmCompareModeCommand(String aMode)
	{
		super(ParserModeContextView.KEY_MODE, aMode);

	}
}
