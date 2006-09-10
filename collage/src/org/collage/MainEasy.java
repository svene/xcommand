package org.collage;

import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;
import org.collage.dom.evaluator.javassist.JavassistTemplate;
import org.collage.dom.evaluator.javassist.JavassistContextView;
import org.collage.dom.evaluator.text.TextTemplate;
import org.collage.template.Template;
import org.xcommand.core.IXCommand;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MainEasy
{
	public static void main(String args[]) throws Exception
	{
		Map ctx = new HashMap();
		ctx.put("firstname", "Uli");

//		ez1(ctx);
//		ez2(ctx);
//		ez3(ctx);
//		ez4(ctx);
//		ez5(ctx);
//		ez6(ctx);
//		java01(ctx);
//		java02(ctx);
//		java03(ctx);
		java04(ctx);
	}

	private static void ez1(Map aCtx)
	{
		Template t = new TextTemplate("hallo ${firstname}.\nWie gehts?\n");
		System.out.println("\n1:");
		String s = t.getStringResult(aCtx);
		System.out.println(s);
	}

	private static void ez2(Map aCtx)
	{
		System.out.println("\n2:");
		Template t = new TextTemplate("hallo ${firstname}.\nWie gehts?\n");
		t.writeTo(aCtx, new PrintWriter(System.out));
	}

	private static void ez3(Map aCtx) throws Exception
	{
		System.out.println("\n3:");
		Template t = new TextTemplate(new FileInputStream(new File("in.txt")));
		t.writeTo(aCtx, new PrintWriter(System.out));
	}

	private static void ez4(Map aCtx)
	{
		System.out.println("\n4:");
		Map cctx = new HashMap();
//		DomNodeCreationHandlerContextView.setProduceJavaSource(cctx, Boolean.FALSE);
		Template t = new TextTemplate("hallo ${firstname}.\nWie gehts?\n", cctx);
		t.writeTo(aCtx, new PrintWriter(System.out));
	}

	private static void ez5(Map aCtx)
	{
		System.out.println("\n5:");
		Map cctx = new HashMap();
		DomNodeCreationHandlerContextView.setProduceJavaSource(cctx, Boolean.FALSE);
		Template t = new TextTemplate("hallo ${firstname}.\nWie gehts?\n", cctx);
		t.writeTo(aCtx, new PrintWriter(System.out));
		aCtx.put("firstname", "Sven");
		t.writeTo(aCtx, new PrintWriter(System.out));
	}
	/** Javassist Template usage */
	private static void ez6(Map aCtx)
	{
		System.out.println("\n6:");
		Map cctx = new HashMap();
		JavassistTemplate t = new JavassistTemplate("hallo ${firstname}.\nWie gehts?\n", cctx);
		t.execute(aCtx);
		IXCommand cmd = JavassistContextView.getTemplateInstance(aCtx);
		System.out.println("-----------");
		Map ctx = new HashMap();
		cmd.execute(ctx);
		ctx.put("firstname", "Sven");
		cmd.execute(ctx);
	}
	private static void java01(Map aCtx)
	{
		System.out.println("\njava01:");
		Map cctx = new HashMap();
		IXCommand cmd = new JavassistTemplate("hallo <?java int i = 1;?> ${firstname}.\nWie gehts?\n", cctx).getInstance();
		System.out.println("-----------");
		Map ctx = new HashMap();
		cmd.execute(ctx);
		ctx.put("firstname", "Sven");
		cmd.execute(ctx);
	}
	private static void java02(Map aCtx)
	{
		System.out.println("\njava02:");
		Map cctx = new HashMap();
		IXCommand cmd = new JavassistTemplate("hallo <?java for (int i = 0; i< 10; i++){ _writer.write(i + \"\");?> ${firstname}.\nWie gehts?\n<?java }?>", cctx).getInstance();

		System.out.println("-----------");
		Map ctx = new HashMap();
		cmd.execute(ctx);
		ctx.put("firstname", "Sven");
		cmd.execute(ctx);
	}
	private static void java03(Map aCtx) throws Exception
	{
		System.out.println("\njava03:");
		IXCommand cmd = new JavassistTemplate(new FileInputStream(new File("java03_in.txt"))).getInstance();

		aCtx.put("firstname", "Sven");
		cmd.execute(aCtx);
	}

	/**
	 * Show how to write to a String instead of to System.out
	 */
	private static void java04(Map aCtx) throws Exception
	{
		System.out.println("\njava04:");
		IXCommand cmd = new JavassistTemplate(new FileInputStream(new File("java03_in.txt"))).getInstance();

		aCtx.put("firstname", "Sven");
		Writer writer = new StringWriter(1024);
		aCtx.put("writer", writer);
		cmd.execute(aCtx);
		String s = writer.toString();
		System.out.println("s:\n" + s);
	}
}
