package org.xcommand.example.commands;

import org.xcommand.ResultContextView;
import org.xcommand.ICommand;

import java.util.Map;

public class CounterCondition implements ICommand
{
	public void execute(Map aContext)
	{
		Integer counter = CountertContextView.getCounter(aContext);
		if (counter != null && counter.intValue() == 10)
		{
			ResultContextView.setResult(aContext, Boolean.TRUE);
		}
		else
		{
			ResultContextView.setResult(aContext, Boolean.FALSE);
		}
	}
}
