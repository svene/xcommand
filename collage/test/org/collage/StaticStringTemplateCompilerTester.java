package org.collage;

import junit.framework.TestCase;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.template.StaticStringTemplateCompiler;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.IXCommand;

import java.util.HashMap;
import java.util.Map;

public class StaticStringTemplateCompilerTester extends TestCase
{

	Map dataCtx;
	private static final int RUNS = 10000;

	protected void setUp() throws Exception
	{
		dataCtx = new HashMap();
		dataCtx.put("firstname", "Uli");
	}

	public void test1()
	{
//		System.out.println("TextTemplateTest.test1()");
		for (int i = 0; i < RUNS; i++)
		{
			IXCommand cmd = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}. Wie gehts?");
			cmd.execute(dataCtx);
			String s = StringHandlerCV.getString(dataCtx);
//			System.out.println(s);
			assertEquals("hallo Uli. Wie gehts?", s);
		}
	}

	public void test2()
	{
		// On first template request `StaticStringTemplateCompiler' will compile unknown template:
//		System.out.println("StaticStringTemplateCompilerTester.test2()");
		IXCommand cmd = new StaticStringTemplateCompiler().getTemplateCommand("hallo ${firstname}. Wie gehts?");
		cmd.execute(dataCtx);
		String s = StringHandlerCV.getString(dataCtx);
//		System.out.println(s);
		assertEquals("hallo Uli. Wie gehts?", s);

		// On second template request `StaticStringTemplateCompiler' should find template in cache: 
		for (int i = 0; i < RUNS; i++)
		{
			cmd = new StaticStringTemplateCompiler().getTemplateCommand("hallo ${firstname}. Wie gehts?");
			cmd.execute(dataCtx);
			s = StringHandlerCV.getString(dataCtx);
//			System.out.println(s);
			assertEquals("hallo Uli. Wie gehts?", s);
		}
	}


}
