package org.collage;

import junit.framework.TestCase;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.template.*;
import org.xcommand.core.IXCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class TextTemplateTest extends TestCase
{
	Map dataCtx;

	protected void setUp() throws Exception
	{
		dataCtx = new HashMap();
		dataCtx.put("firstname", "Uli");
	}

	public void test1()
	{
		System.out.println("TextTemplateTest.test1()");
		IXCommand cmd = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");
		cmd.execute(dataCtx);
		String s = StringHandlerCV.getString(dataCtx);
		System.out.println(s);
		assertEquals("hallo Uli.\\nWie gehts?\\n", s);
	}

	public void test2()
	{
		System.out.println("\nTextTemplateTest.test2()");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");
		PrintWriter pw = new PrintWriter(System.out);
		tc.setWriter(pw);
		tc.execute(dataCtx);
	}

	public void test3() throws Exception
	{
		System.out.println("\n3:");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(
			new TemplateSource(new FileInputStream(new File("in.txt"))));
		PrintWriter pw = new PrintWriter(System.out);
		tc.setWriter(pw);
		tc.execute(dataCtx);
	}

	public void test4()
	{
		System.out.println("\n4:");
		Map cctx = new HashMap();
//		DomNodeCreationHandlerCV.setProduceJavaSource(cctx, Boolean.FALSE);
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(
			"hallo ${firstname}.\nWie gehts?\n", cctx));
		PrintWriter pw = new PrintWriter(System.out);
		tc.setWriter(pw);
		tc.execute(dataCtx);
	}

	public void test5()
	{
		System.out.println("\n5:");
		Map cctx = new HashMap();
		DomNodeCreationHandlerCV.setProduceJavaSource(cctx, Boolean.FALSE);
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(
			"hallo ${firstname}.\nWie gehts?\n", cctx));
		PrintWriter pw = new PrintWriter(System.out);
		tc.setWriter(pw);
		tc.execute(dataCtx);
		dataCtx.put("firstname", "Sven");
		tc.execute(dataCtx);
	}
	/**
	 * Demonstrate recursive template resolution
	 */
	public void testJava5() throws Exception
	{
		String sOld = "";
		System.out.println("\njava5:");
		Map ctx = new HashMap();
		ctx.put("name", "${firstname} ${lastname}");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(
			new FileInputStream(new File("java05_in.txt")), ctx));
		tc.execute(ctx);
		String s = StringHandlerCV.getString(ctx);
		System.out.println("---\n" + s);
		while (!sOld.equals(s))
		{
			sOld = s;
			tc = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(s, ctx));
			tc.execute(ctx);
			s = StringHandlerCV.getString(ctx);

			System.out.println("---\n" + s);
		}
		ctx.put("firstname", "Uli");
		ctx.put("lastname", "Ehrke");
		tc.execute(ctx);
		s = StringHandlerCV.getString(ctx);
		System.out.println("---\n" + s);
	}

// --- OLD ---

//	public void test1_()
//	{
//		Template t = new TextTemplate(new TemplateSource("hallo ${firstname}.\nWie gehts?\n"));
//		System.out.println("\n1:");
//		String s = t.getStringResult(dataCtx);
//		System.out.println(s);
//		assertEquals("hallo Uli.\\nWie gehts?\\n", s);
//	}

//	public void test5Org()
//	{
//		System.out.println("\n5:");
//		Map cctx = new HashMap();
//		DomNodeCreationHandlerCV.setProduceJavaSource(cctx, Boolean.FALSE);
//		Template t = new TextTemplate(new TemplateSource("hallo ${firstname}.\nWie gehts?\n", cctx));
//		t.writeTo(dataCtx, new PrintWriter(System.out));
//		dataCtx.put("firstname", "Sven");
//		t.writeTo(dataCtx, new PrintWriter(System.out));
//	}

}
