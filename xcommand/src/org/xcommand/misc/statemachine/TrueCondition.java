package org.xcommand.misc.statemachine;

import org.xcommand.core.ResultContextView;
import org.xcommand.core.IXCommand;

import java.util.Map;

public class TrueCondition implements IXCommand
{
	public void execute(Map aContext)
	{
		ResultContextView.setResult(aContext, Boolean.TRUE);
	}
}
