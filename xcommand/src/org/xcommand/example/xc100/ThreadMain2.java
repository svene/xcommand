package org.xcommand.example.xc100;

import org.xcommand.misc.TC;

/**
 * This example demonstrates how the ThreadContext class <code>TC</code> can be used
 * to use a thread-attached context in a very convenient way.
 */
public class ThreadMain2
{

	public static void main(String[] args)
	{
		new PersonCV(TC.getInstance()).setName("Sven");
		new ThreadMain2().execute();
	}

	private void execute()
	{
		String name = new PersonCV(TC.getInstance()).getName();
		System.out.println("name = " + name);
	}

}
