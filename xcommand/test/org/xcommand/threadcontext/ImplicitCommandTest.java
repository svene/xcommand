package org.xcommand.threadcontext;

import junit.framework.TestCase;
import org.xcommand.core.IImplicitXCommand;
import org.xcommand.core.TCP;
import org.xcommand.misc.InheritableMap;

import java.util.HashMap;
import java.util.Map;

public class ImplicitCommandTest extends TestCase
{

	static String s = "hello world, from '" + ImplicitCommandTest.class.getName() + "'";


	public void test1()
	{
		TIn2OutCV.setInput(s);
		IImplicitXCommand cmd = new TIn2OutCommand();
		cmd.execute();
		assertEquals(s, TIn2OutCV.getOutput());

		Map savedCtx = TCP.getContext();

		Map ctx = new HashMap();
		TCP.setContext(ctx);
		assertSame(ctx, TCP.getContext());

		String sven = "sven";
		TIn2OutCV.setInput(sven);
		cmd.execute();
		assertEquals(sven, TIn2OutCV.getOutput());

		TCP.setContext(savedCtx);
		assertSame(savedCtx, TCP.getContext());
		assertEquals(s, TIn2OutCV.getOutput());

		System.out.println("===");
		InheritableMap im = new InheritableMap(savedCtx);
		TCP.setContext(im);
		assertSame(im, TCP.getContext());
		assertEquals(s, TIn2OutCV.getOutput());

		TIn2OutCV.setInput(sven);
		assertEquals(s, TIn2OutCV.getOutput());
		cmd.execute();
		assertEquals(sven, TIn2OutCV.getOutput());

		TCP.setContext(savedCtx);
		assertSame(savedCtx, TCP.getContext());
		assertEquals(s, TIn2OutCV.getOutput());
	}

	/** test with two different threads */
	public void test2()
	{
		final IImplicitXCommand cmd = new TIn2OutCommand();
		Runnable r1 = new Runnable()
		{
			public void run()
			{
				TIn2OutCV.setInput("runnable 1");
				cmd.execute();
				System.out.println("runnable 1: " + TIn2OutCV.getOutput());
				System.out.flush();
			}
		};
		TIn2OutCV.setInput("main thread");					
		cmd.execute();

		// Note that in the following two lines `t1' does not overwrite the context of the main thread:
		Thread t1 = new Thread(r1);
		t1.start();
		System.out.println("main thread: " + TIn2OutCV.getOutput());

	}

}
