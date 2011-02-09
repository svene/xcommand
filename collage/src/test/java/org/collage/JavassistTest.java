package org.collage;

import junit.framework.TestCase;
import org.collage.template.*;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.*;
import org.xcommand.util.ResourceUtil;

import java.io.*;
import java.util.HashMap;

public class JavassistTest extends TestCase
{
	protected void setUp() throws Exception
	{
		TCP.pushContext(new HashMap());
		TCP.getContext().put("firstname", "Uli");
	}

	protected void tearDown() throws Exception
	{
		TCP.popContext();
	}

	/* Create a template command from a string, execute it and write output to System.out */
	public void test1()
	{
		System.out.println("JavassistTest.test1()");
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie geht's?\n");

		System.out.println("-----------");
		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
//!!		assertNull(StringHandlerCV.getString(TCP.getContext()));
	}

	/* Create a template command via TemplateSouce, execute it and write output to StringWriter (to be able to unittest result) */
	public void test1UsingWriter()
	{
		System.out.println("JavassistTest.test1UsingWriter()");
		TemplateSource ts = new TemplateSource("hallo ${firstname}.\nWie geht's?\n");
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommand(ts);

		System.out.println("-----------");
		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
//!!		assertNull(StringHandlerCV.getString(TCP.getContext()));
		assertEquals("hallo Sven.\nWie geht's?\n", sw.toString());
	}
	public void testJava1()
	{
		System.out.println("JavassistTest.testJava1()");
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString("hallo <?java int i = 1;?> ${firstname}.\\nWie geht's?\\n");

		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertEquals("hallo  Sven.\nWie geht's?\n", sw.toString());
	}

	public void testJava2()
	{
		System.out.println("JavassistTest.testJava2()");
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(
			"hallo\n<?java for (int i = 0; i< 3; i++){ _writer.write(i + \"\");?> ${firstname}.\nWie geht's?\n<?java }?>");

		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
		assertEquals("hallo\n0 Sven.\nWie geht's?\n1 Sven.\nWie geht's?\n2 Sven.\nWie geht's?\n", sw.toString());
	}
	public void testJava3() throws Exception
	{
		System.out.println("JavassistTest.testJava3()");
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromStream(
			ResourceUtil.newInputStreamFromFilename("java03_in.txt"));

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

	/**
	 * Demonstrate recursive template resolution
	 */
	public void testJavaRecursiveInline() throws Exception
	{
		String sOld = "";
		System.out.println("\njava5b:");
		TCP.getContext().put("name", "${firstname} ${lastname}");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(
			new TemplateSource(ResourceUtil.newInputStreamFromFilename("java05_in.txt")));
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
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommand(new TemplateSource(s));
//		IXCommand cmd = new JavassistTemplate(new TemplateSource(s, TCP.getContext())).getInstance();
		TCP.getContext().put("firstname", "Uli");
		TCP.getContext().put("lastname", "Ehrke");
		System.out.println("---");
		cmd.execute();
	}

	/**
	 * Demonstrate recursive template resolution
	 */
	public void testJavaRecursive() throws Exception
	{
		System.out.println("\njava6:");
		TCP.pushContext(new HashMap());
		TCP.getContext().put("name", "${firstname} ${lastname}");

		TemplateSource ts = new TemplateSource(ResourceUtil.newInputStreamFromFilename("java06_in.txt"));
		TCP.popContext();
		ICommand cmd = TemplateFactory.newRecursiveTemplateInstance(ts);
		TCP.getContext().put("firstname", "Uli");
		TCP.getContext().put("lastname", "Ehrke");
		System.out.println("---");
		cmd.execute();
	}

	public void testJava7() throws Exception
	{
		System.out.println("\njava7:");
		String address = fileContent("java07_address.txt");
		String nameAndAddress = fileContent("java07_nameandaddress.txt");
		TCP.pushContext(new HashMap());
		TCP.getContext().put("address", address);
		TCP.getContext().put("person", nameAndAddress);

		String person = fileContent("java07_person.txt");
		TemplateSource ts = new TemplateSource(person);
		TCP.popContext();
		ICommand cmd = TemplateFactory.newRecursiveTemplateInstance(ts);
		System.out.println("---");
		cmd.execute();

		TCP.getContext().put("firstname", "Uli");
		TCP.getContext().put("lastname", "Ehrke");
		System.out.println("---");
		cmd.execute();
	}

// --- Implementation ---

	private String fileContent(String aFilename) throws Exception
	{
		InputStream fis = ResourceUtil.newInputStreamFromFilename(aFilename);
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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);

}
