package org.collage.csm;

import org.collage.parser.ParserModeCV;
import org.xcommand.misc.statemachine.CompareModeCommand;

public class CsmCompareModeCommand extends CompareModeCommand
{
	public CsmCompareModeCommand(String aMode)
	{
		super(ParserModeCV.KEY_MODE, aMode);

	}
}
