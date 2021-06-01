package org.xcommand.example.xc100;

import org.xcommand.api.ContextViews;

public class EchoExample
{

	public static void main(String[] args)
	{
		new EchoExample().execute();
	}

	public void execute()
	{
		ContextViews.get().echoCV.setMessage("Hi! I am a xcommand example. And who are you?");
		ContextViews.get().echoCommand.execute();
	}

}
