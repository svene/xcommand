package org.collage;

import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;
import java.io.*;

import org.collage.template.Template;
import org.collage.template.TemplateSource;
import org.collage.template.TemplateFactory;
import org.collage.dom.evaluator.text.TextTemplate;
import org.collage.dom.evaluator.javassist.JavassistTemplate;
import org.collage.dom.evaluator.javassist.JavassistContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;
import org.xcommand.core.IXCommand;

public class EasyTest extends TestCase
{
	Map dataCtx;

	protected void setUp() throws Exception
	{
		dataCtx = new HashMap();
		dataCtx.put("firstname", "Uli");
	}

	public void test1()
	{
		Template t = new TextTemplate(new TemplateSource("hallo ${firstname}.\nWie gehts?\n"));
		System.out.println("\n1:");
		String s = t.getStringResult(dataCtx);
		System.out.println(s);
	}

	public void test2()
	{
		System.out.println("\n2:");
		Template t = new TextTemplate(new TemplateSource("hallo ${firstname}.\nWie gehts?\n"));
		t.writeTo(dataCtx, new PrintWriter(System.out));
	}

	public void test3() throws Exception
	{
		System.out.println("\n3:");
		Template t = new TextTemplate(new TemplateSource(new FileInputStream(new File("in.txt"))));
		t.writeTo(dataCtx, new PrintWriter(System.out));
	}

	public void test4()
	{
		System.out.println("\n4:");
		Map cctx = new HashMap();
//		DomNodeCreationHandlerContextView.setProduceJavaSource(cctx, Boolean.FALSE);
		Template t = new TextTemplate(new TemplateSource("hallo ${firstname}.\nWie gehts?\n", cctx));
		t.writeTo(dataCtx, new PrintWriter(System.out));
	}

	public void test5()
	{
		System.out.println("\n5:");
		Map cctx = new HashMap();
		DomNodeCreationHandlerContextView.setProduceJavaSource(cctx, Boolean.FALSE);
		Template t = new TextTemplate(new TemplateSource("hallo ${firstname}.\nWie gehts?\n", cctx));
		t.writeTo(dataCtx, new PrintWriter(System.out));
		dataCtx.put("firstname", "Sven");
		t.writeTo(dataCtx, new PrintWriter(System.out));
	}
	/** Javassist Template usage */
	public void test6()
	{
		System.out.println("\n6:");
		Map cctx = new HashMap();
		JavassistTemplate t = new JavassistTemplate(new TemplateSource("hallo ${firstname}.\nWie gehts?\n", cctx));
		t.execute(dataCtx);
		IXCommand cmd = JavassistContextView.getTemplateInstance(dataCtx);
		System.out.println("-----------");
		Map ctx = new HashMap();
		cmd.execute(ctx);
		ctx.put("firstname", "Sven");
		cmd.execute(ctx);
	}
	public void testJava1()
	{
		System.out.println("\njava01:");
		Map cctx = new HashMap();
		IXCommand cmd = new JavassistTemplate(new TemplateSource("hallo <?java int i = 1;?> ${firstname}.\nWie gehts?\n", cctx)).getInstance();
		System.out.println("-----------");
		Map ctx = new HashMap();
		cmd.execute(ctx);
		ctx.put("firstname", "Sven");
		cmd.execute(ctx);
	}
	public void testJava2()
	{
		System.out.println("\njava02:");
		Map cctx = new HashMap();
		IXCommand cmd = new JavassistTemplate(new TemplateSource("hallo <?java for (int i = 0; i< 10; i++){ _writer.write(i + \"\");?> ${firstname}.\nWie gehts?\n<?java }?>", cctx)).getInstance();

		System.out.println("-----------");
		Map ctx = new HashMap();
		cmd.execute(ctx);
		ctx.put("firstname", "Sven");
		cmd.execute(ctx);
	}
	public void testJava3() throws Exception
	{
		System.out.println("\njava03:");
		IXCommand cmd = new JavassistTemplate(new TemplateSource(new FileInputStream(new File("java03_in.txt")))).getInstance();

		dataCtx.put("firstname", "Sven");
		cmd.execute(dataCtx);
	}

	/**
	 * Show how to write to a String instead of to System.out
	 */
	public void testJava4() throws Exception
	{
		System.out.println("\njava04:");
		IXCommand cmd = new JavassistTemplate(new TemplateSource(new FileInputStream(new File("java03_in.txt")))).getInstance();

		dataCtx.put("firstname", "Sven");
		Writer writer = new StringWriter(1024);
		dataCtx.put("writer", writer);
		cmd.execute(dataCtx);
		String s = writer.toString();
		System.out.println("s:\n" + s);
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
		Template t = new TextTemplate(new TemplateSource(new FileInputStream(new File("java05_in.txt")), ctx));
		String s = t.getStringResult(ctx);
		System.out.println("---\n" + s);
		while (!sOld.equals(s))
		{
			sOld = s;
			t = new TextTemplate(new TemplateSource(s, ctx));
			s = t.getStringResult(ctx);
			System.out.println("---\n" + s);
		}
		ctx.put("firstname", "Uli");
		ctx.put("lastname", "Ehrke");
		s = t.getStringResult(ctx);
		System.out.println("---\n" + s);
	}

	/**
	 * Demonstrate recursive template resolution
	 */
	public void testJava5b() throws Exception
	{
		String sOld = "";
		System.out.println("\njava5b:");
		Map ctx = new HashMap();
		ctx.put("name", "${firstname} ${lastname}");
		Template t = new TextTemplate(new TemplateSource(new FileInputStream(new File("java05_in.txt")), ctx));
		String s = t.getStringResult(ctx);
		System.out.println("---\n" + s);
		while (!sOld.equals(s))
		{
			sOld = s;
			t = new TextTemplate(new TemplateSource(s, ctx));
			s = t.getStringResult(ctx);
			System.out.println("---\n" + s);
		}
		IXCommand cmd = new JavassistTemplate(new TemplateSource(s, ctx)).getInstance();
		ctx.put("firstname", "Uli");
		ctx.put("lastname", "Ehrke");
		System.out.println("---");
		cmd.execute(ctx);
	}
	/**
	 * Demonstrate recursive template resolution
	 */
	public void testJava6() throws Exception
	{
		System.out.println("\njava6:");
		Map cctx = new HashMap();
		cctx.put("name", "${firstname} ${lastname}");

		TemplateSource ts = new TemplateSource(new FileInputStream("java06_in.txt"), cctx);
		IXCommand cmd = TemplateFactory.newRecursiveTemplateInstance(ts);
		Map ctx = new HashMap();
		ctx.put("firstname", "Uli");
		ctx.put("lastname", "Ehrke");
		System.out.println("---");
		cmd.execute(ctx);
	}
	public void testJava7() throws Exception
	{
		System.out.println("\njava7:");
		String address = fileContent("java07_address.txt");
		String nameAndAddress = fileContent("java07_nameandaddress.txt");
		Map cctx = new HashMap();
		cctx.put("address", address);
		cctx.put("person", nameAndAddress);

		String person = fileContent("java07_person.txt");
		TemplateSource ts = new TemplateSource(person, cctx);
		IXCommand cmd = TemplateFactory.newRecursiveTemplateInstance(ts);
		Map ctx = new HashMap();
		System.out.println("---");
		cmd.execute(ctx);

		ctx.put("firstname", "Uli");
		ctx.put("lastname", "Ehrke");
		System.out.println("---");
		cmd.execute(ctx);
	}
	private String fileContent(String aFilename) throws Exception
	{
		FileInputStream fis = new FileInputStream(aFilename);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		writeInputStreamToOutputStream(fis, bos);
		return bos.toString();
	}

	private static void writeInputStreamToOutputStream(InputStream a_is, OutputStream a_os)
		throws IOException
	{
		byte[] buffer = new byte[4096];
		int bytes_read;
		while ((bytes_read = a_is.read(buffer)) != -1)
		{
			a_os.write(buffer, 0, bytes_read);
		}
	}
}
