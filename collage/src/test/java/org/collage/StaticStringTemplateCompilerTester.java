package org.collage;

import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.StaticStringTemplateCompiler;
import org.collage.template.TextTemplateCompiler;
import org.junit.Before;
import org.junit.Test;
import org.xcommand.core.*;

import static org.junit.Assert.assertEquals;

public class StaticStringTemplateCompilerTester
{

	private static final int RUNS = 10000;

	@Before
	protected void initializeContext() throws Exception
	{
		TCP.getContext().put("firstname", "Uli");
	}

	@Test
	public void test1()
	{
//		System.out.println("TextTemplateTest.test1()");
		for (int i = 0; i < RUNS; i++)
		{
			ICommand cmd = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}. Wie gehts?");
			cmd.execute();
			String s = stringHandlerCV.getString();
//			System.out.println(s);
			assertEquals("hallo Uli. Wie gehts?", s);
		}
	}

	@Test public void test2()
	{
		// On first template request `StaticStringTemplateCompiler' will compile unknown template:
//		System.out.println("StaticStringTemplateCompilerTester.test2()");
		ICommand cmd = new StaticStringTemplateCompiler().getTemplateCommand("hallo ${firstname}. Wie gehts?");
		cmd.execute();
		String s = stringHandlerCV.getString();
//		System.out.println(s);
		assertEquals("hallo Uli. Wie gehts?", s);

		// For further template request `StaticStringTemplateCompiler' should find template in cache: 
		for (int i = 0; i < RUNS; i++)
		{
			cmd = new StaticStringTemplateCompiler().getTemplateCommand("hallo ${firstname}. Wie gehts?");
			cmd.execute();
			s = stringHandlerCV.getString();
//			System.out.println(s);
			assertEquals("hallo Uli. Wie gehts?", s);
		}
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}
