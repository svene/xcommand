package org.xcommand.example.commands;

import java.util.Map;

public class CounterCommandStateful extends StatefulEchoCommand
{

	public CounterCommandStateful(String message)
	{
		super(message);
	}

	public void execute(Map aContext)
	{
		Integer counter = CountertContextView.getCounter(aContext);
		if (counter == null)
		{
			counter = new Integer(0);
		}
		counter = new Integer(counter.intValue() + 1);
		CountertContextView.setCounter(aContext, counter);
		setMessage(counter.intValue() + "");
		super.execute(aContext);
	}
}
