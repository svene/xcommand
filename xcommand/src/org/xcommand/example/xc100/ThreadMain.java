package org.xcommand.example.xc100;

/**
 * This example demonstrates how the context can be attached to the current
 * thread and thus no argument passing is needed at all.
 */
public class ThreadMain
{

	public static void main(String[] args)
	{
		ThreadGlobal ctx = new ThreadGlobal();
		ctx.put("name", "Sven");
		new ThreadMain().execute();
	}

	private void execute()
	{
		ThreadGlobal ctx = new ThreadGlobal();
		String name = (String) ctx.get("name");
		System.out.println("name = " + name);
	}

}
