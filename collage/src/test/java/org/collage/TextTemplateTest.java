package org.collage;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.JavassistTemplateCompiler;
import org.collage.template.TemplateCV;
import org.collage.template.TemplateCommand;
import org.collage.template.TextTemplateCompiler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xcommand.core.*;

import java.io.StringWriter;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TextTemplateTest
{
	StringWriter sw;

	@Before
	public void initializeContext() throws Exception
	{
		TCP.pushContext(new HashMap());
		TCP.getContext().put("firstname", "Uli");

		sw = new StringWriter();
	}

	@After
	public void tearDownContext() throws Exception
	{
		TCP.popContext();
	}

	@Test
	public void test1()
	{
		new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n").execute();
		assertEquals("hallo Uli.\nWie gehts?\n", stringHandlerCV.getString());
	}

	@Test
	public void test2()
	{
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");
		tc.setWriter(sw);
		tc.execute();
		assertEquals("hallo Uli.\nWie gehts?\n", sw.toString());
	}

	@Test
	public void verfiyProperFunctionIfInputComesFromFile() throws Exception
	{
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommandFromResourceName("in.txt");
		tc.setWriter(sw);
		tc.execute();
		assertEquals("Hallo Uli! Willkommen bei uns.\n<?java int i = 1 ?>d\n", sw.toString());
	}

	@Test
	public void test5()
	{
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");

		TCP.getContext().put("firstname", "Sven");
		tc.setWriter(sw);
		tc.execute();
		assertEquals("hallo Sven.\nWie gehts?\n", sw.toString());
	}

	/**
	 * Demonstrate recursive template resolution
	 */
	@Test
	public void testRecursiveInlining() throws Exception
	{
		TCP.getContext().put("name", "${firstname} ${lastname}");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${name}. Wie gehts?");
		tc.execute();
		String s = stringHandlerCV.getString();
		assertEquals("hallo ${firstname} ${lastname}. Wie gehts?", s);

		tc = new TextTemplateCompiler().newTemplateCommandFromString(s);
		tc.execute();
		s = stringHandlerCV.getString();
		assertEquals("hallo Uli ${lastname}. Wie gehts?", s);

		tc = new TextTemplateCompiler().newTemplateCommandFromString(s);
		tc.execute();
		s = stringHandlerCV.getString();
		assertEquals("hallo Uli ${lastname}. Wie gehts?", s);

		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(s);
		TCP.getContext().put("firstname", "Uli");
		TCP.getContext().put("lastname", "Ehrke");
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertEquals("hallo Uli Ehrke. Wie gehts?", sw.toString());
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
