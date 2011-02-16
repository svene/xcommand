package org.collage;

import junit.framework.TestCase;
import org.collage.template.TemplateCommand;
import org.collage.template.TemplateSource;
import org.collage.template.TextTemplateCompiler;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xcommand.core.*;
import org.xcommand.util.ResourceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TextTemplateTest
{
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

	public void test1()
	{
		System.out.println("TextTemplateTest.test1()");
		ICommand cmd = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");
		cmd.execute();
		String s = stringHandlerCV.getString();
		System.out.println(s);
		assertEquals("hallo Uli.\nWie gehts?\n", s);
	}

	@Test
	public void test2()
	{
		System.out.println("\nTextTemplateTest.test2()");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");
		PrintWriter pw = new PrintWriter(System.out);
		tc.setWriter(pw);
		tc.execute();
	}

	@Test public void test3() throws Exception
	{
		System.out.println("\n3:");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(
			new TemplateSource(ResourceUtil.newInputStreamFromFilename("in.txt")));
		PrintWriter pw = new PrintWriter(System.out);
		tc.setWriter(pw);
		tc.execute();
	}

	@Test public void test4()
	{
		System.out.println("\n4:");
		TCP.pushContext(new HashMap());
//		DomNodeCreationHandlerCV.setProduceJavaSource(cctx, Boolean.FALSE);
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(
			"hallo ${firstname}.\nWie gehts?\n"));
		TCP.popContext();
		PrintWriter pw = new PrintWriter(System.out);
		tc.setWriter(pw);
		tc.execute();
	}

	@Test public void test5()
	{
		System.out.println("\n5:");
		TCP.pushContext(new HashMap());
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(
			"hallo ${firstname}.\nWie gehts?\n"));
		TCP.popContext();
		PrintWriter pw = new PrintWriter(System.out);
		tc.setWriter(pw);
		tc.execute();
		TCP.getContext().put("firstname", "Sven");
		tc.execute();
	}
	/**
	 * Demonstrate recursive template resolution
	 */
	@Test public void testJava5() throws Exception
	{
		String sOld = "";
		System.out.println("\njava5:");
		TCP.pushContext(new HashMap());
		TCP.getContext().put("name", "${firstname} ${lastname}");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(
			ResourceUtil.newInputStreamFromFilename("java05_in.txt")));
		tc.execute();
		String s = stringHandlerCV.getString();
		System.out.println("---\n" + s);
		while (!sOld.equals(s))
		{
			sOld = s;
			tc = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(s));
			tc.execute();
			s = stringHandlerCV.getString();

			System.out.println("---\n" + s);
		}
		TCP.getContext().put("firstname", "Uli");
		TCP.getContext().put("lastname", "Ehrke");
		tc.execute();
		s = stringHandlerCV.getString();
		System.out.println("---\n" + s);
		TCP.popContext();
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
