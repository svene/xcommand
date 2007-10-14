package org.collage;

import junit.framework.TestCase;
import org.collage.template.*;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.xcommand.core.IXCommand;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JavassistTest extends TestCase
{
	Map dataCtx;

	protected void setUp() throws Exception
	{
		dataCtx = new HashMap();
		dataCtx.put("firstname", "Uli");
	}
	/* Create a template command from a string, execute it and write output to System.out */
	public void test1()
	{
		System.out.println("JavassistTest.test1()");
		IXCommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie geht's?\n");

		System.out.println("-----------");
		Map ctx = new HashMap();
		ctx.put("firstname", "Sven");
		cmd.execute(ctx);
		assertNull(StringHandlerCV.getString(ctx));
	}
	/* Create a template command via TemplateSouce, execute it and write output to StringWriter (to be able to unittest result) */
	public void test1UsingWriter()
	{
		System.out.println("JavassistTest.test1UsingWriter()");
		TemplateSource ts = new TemplateSource("hallo ${firstname}.\nWie geht's?\n");
		IXCommand cmd = new JavassistTemplateCompiler().newTemplateCommand(ts);

		System.out.println("-----------");
		Map ctx = new HashMap();
		ctx.put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(ctx, sw);
		cmd.execute(ctx);
		assertNull(StringHandlerCV.getString(ctx));
		assertEquals("hallo Sven.\nWie geht's?\n", sw.toString());
	}
	public void testJava1()
	{
		System.out.println("JavassistTest.testJava1()");
		IXCommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString("hallo <?java int i = 1;?> ${firstname}.\\nWie geht's?\\n");

		Map ctx = new HashMap();
		ctx.put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(ctx, sw);
		cmd.execute(ctx);
		assertEquals("hallo  Sven.\nWie geht's?\n", sw.toString());
	}
	public void testJava2()
	{
		System.out.println("JavassistTest.testJava2()");
		IXCommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(
			"hallo\n<?java for (int i = 0; i< 3; i++){ _writer.write(i + \"\");?> ${firstname}.\nWie geht's?\n<?java }?>");

		Map ctx = new HashMap();
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(ctx, sw);
		ctx.put("firstname", "Sven");
		cmd.execute(ctx);
		assertEquals("hallo\n0 Sven.\nWie geht's?\n1 Sven.\nWie geht's?\n2 Sven.\nWie geht's?\n", sw.toString());
	}
	public void testJava3() throws Exception
	{
		System.out.println("JavassistTest.testJava3()");
		IXCommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromStream(
			new FileInputStream(new File("java03_in.txt")));

		String s = "";
		for (int i = 0; i < 10; i++)
		{
			s  += i + ": Hallo Sven. Wie gehts?\n";
		}
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(dataCtx, sw);

		dataCtx.put("firstname", "Sven");
		cmd.execute(dataCtx);
		assertEquals(s, sw.toString());
	}

	/**
	 * Demonstrate recursive template resolution
	 */
	public void testJavaRecursiveInline() throws Exception
	{
		String sOld = "";
		System.out.println("\njava5b:");
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
		IXCommand cmd = new JavassistTemplateCompiler().newTemplateCommand(new TemplateSource(s, ctx));
//		IXCommand cmd = new JavassistTemplate(new TemplateSource(s, ctx)).getInstance();
		ctx.put("firstname", "Uli");
		ctx.put("lastname", "Ehrke");
		System.out.println("---");
		cmd.execute(ctx);
	}

	/**
	 * Demonstrate recursive template resolution
	 */
	public void testJavaRecursive() throws Exception
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

// TODO: continue here (14.10.2007)
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

// --- Implementation ---

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
