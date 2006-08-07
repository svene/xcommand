package org.xcommand.example.xc110;

public class DynamicEchoContextView extends DynamicContextView
{

// --- Access ---

	public String getMessage()
	{
		return (String) context.get(DynamicEchoContextView.KEY_MESSAGE);
	}

// --- Setting ---

	public void setMessage(String aMessage)
	{
		context.put(DynamicEchoContextView.KEY_MESSAGE, aMessage);
	}

// --- Implementation ---

	public static final String KEY_MESSAGE = "message";
}
