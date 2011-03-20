package org.collage;

import org.collage.template.*;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xcommand.core.*;
import org.xcommand.util.ResourceUtil;

import java.io.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class JavassistTest
{
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);

	@Before
	public void initializeContext() throws Exception
	{
		TCP.pushContext(new HashMap());
		TCP.getContext().put("firstname", "Uli");
	}

	@After
	public void tearDownContext() throws Exception
	{
		TCP.popContext();
	}

	/* Create a template command from a string, execute it and write output to System.out.
	 * Note: by default the code in 'execute_method.txt' writes to System.out */
	@Test
	public void exerciseNewTemplateCommandFromStringUsingSystemOut()
	{
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie geht's?\n");

		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
	}

	/* Create a template command via TemplateSouce, execute it and write output to StringWriter (to be able to unittest result) */
	@Test public void testNewTemplateCommandFromString()
	{
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie geht's?\n");

		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertEquals("hallo Sven.\nWie geht's?\n", sw.toString());
	}

	@Test public void testNewTemplateCommandFromStringWithNOPJava()
	{
		final String s = "hallo <?java int i = 1;?> ${firstname}.\\nWie geht's?\\n";
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(s);

		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertEquals("hallo  Sven.\nWie geht's?\n", sw.toString());
	}

	@Test public void testNewTemplateCommandFromStringWithEffectiveJava()
	{
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(
			"hallo\n<?java for (int i = 0; i< 3; i++){ _writer.write(i + \"\");?> ${firstname}.\nWie geht's?\n<?java }?>");

		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
		assertEquals("hallo\n0 Sven.\nWie geht's?\n1 Sven.\nWie geht's?\n2 Sven.\nWie geht's?\n", sw.toString());
	}

	@Test public void testNewTemplateCommandFromFileWithEffectiveJava() throws Exception
	{
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromStream(
			ResourceUtil.newInputStreamFromResourceLocation("java03_in.txt"));

		String s = "";
		for (int i = 0; i < 10; i++)
		{
			s  += i + ": Hallo Sven. Wie gehts?\n";
		}

		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);

		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
		assertEquals(s, sw.toString());
	}


}
