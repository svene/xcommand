package org.collage;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.JavassistTemplateCompiler;
import org.collage.template.TemplateCV;
import org.collage.template.TemplateCommand;
import org.collage.template.TextTemplateCompiler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xcommand.core.*;

import java.io.StringWriter;
import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTemplateTest
{
	StringWriter stringWriter;

	@BeforeEach
	public void initializeContext()
	{
		TCP.pushContext(new HashMap());
		TCP.getContext().put("firstname", "Uli");

		stringWriter = new StringWriter();
	}

	@AfterEach
	public void tearDownContext()
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
		tc.setWriter(stringWriter);
		tc.execute();
		assertEquals("hallo Uli.\nWie gehts?\n", stringWriter.toString());
	}

	@Test
	public void verfiyProperFunctionIfInputComesFromFile() {
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommandFromResourceName("in.txt");
		tc.setWriter(stringWriter);
		tc.execute();
		assertEquals("Hallo Uli! Willkommen bei uns.\n<?java int i = 1 ?>d\n", stringWriter.toString());
	}

	@Test
	public void test5()
	{
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");

		TCP.getContext().put("firstname", "Sven");
		tc.setWriter(stringWriter);
		tc.execute();
		assertEquals("hallo Sven.\nWie gehts?\n", stringWriter.toString());
	}

	/**
	 * Demonstrate recursive template resolution
	 */
	@Test
	public void testRecursiveInlining() {
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
		TemplateCV.setWriter(stringWriter);
		cmd.execute();
		assertEquals("hallo Uli Ehrke. Wie gehts?", stringWriter.toString());
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	final IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
	final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
}
