package org.collage.csm;

import org.collage.parser.IParserModeCV;
import org.xcommand.misc.statemachine.CompareModeCommand;

/**
 * TODO: seems not to be used anymore
 */
public class CsmCompareModeCommand extends CompareModeCommand {
	public CsmCompareModeCommand(String aMode) {
		super(IParserModeCV.KEY_MODE, aMode);
	}
}
