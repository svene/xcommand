package org.collage;

import junit.framework.TestCase;
import org.collage.template.StaticStringTemplateCompiler;
import org.collage.template.TextTemplateCompiler;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.*;

public class StaticStringTemplateCompilerTester extends TestCase
{

	private static final int RUNS = 10000;

	protected void setUp() throws Exception
	{
		TCP.getContext().put("firstname", "Uli");
	}

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

	public void test2()
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
