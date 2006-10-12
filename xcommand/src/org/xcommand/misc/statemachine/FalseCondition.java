package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;
import org.xcommand.core.ResultContextView;

import java.util.Map;

public class FalseCondition implements IXCommand
{
	public void execute(Map aCtx)
	{
		ResultContextView.setResult(aCtx, Boolean.FALSE);
	}
}
