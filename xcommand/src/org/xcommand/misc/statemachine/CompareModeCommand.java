package org.xcommand.misc.statemachine;

import org.xcommand.core.multi.ModeContextView;
import org.xcommand.core.ResultContextView;
import org.xcommand.core.IXCommand;

import java.util.Map;

public class CompareModeCommand implements IXCommand
{

// --- Initialization ---

	public CompareModeCommand(String aMode)
	{
		this(ModeContextView.KEY_MODE, aMode);
		mode = aMode;
	}

	public CompareModeCommand(String aModeKey, String aMode)
	{
		modeKey = aModeKey;
		mode = aMode;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		String ctxMode = (String) aCtx.get(modeKey);
		Boolean result = mode.equals(ctxMode) ? Boolean.TRUE : Boolean.FALSE;
		ResultContextView.setResult(aCtx, result);
	}

// --- Implementation ---

	private String modeKey;
	private String mode;
}
