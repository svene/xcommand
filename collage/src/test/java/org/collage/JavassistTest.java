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
		System.out.println("JavassistTest.test1()");
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie geht's?\n");

		System.out.println("-----------");
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

	/**
	 * Demonstrate recursive template resolution
	 */
	@Test public void testJavaRecursiveInline() throws Exception
	{
		TCP.getContext().put("name", "${firstname} ${lastname}");
		TemplateCommand tc = new TextTemplateCompiler().newTemplateCommand(
			new TemplateSource(ResourceUtil.newInputStreamFromResourceLocation("java05_in.txt"))); // file content: hallo ${name}. Wie gehts?
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

		System.out.println("---\n" + s);
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(s);
		TCP.getContext().put("firstname", "Uli");
		TCP.getContext().put("lastname", "Ehrke");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertEquals("hallo Uli Ehrke. Wie gehts?", sw.toString());
	}

	/**
	 * Demonstrate recursive template resolution
	 * @deprecated moved to 'JavassistSpockTest'
	 */
	@Test public void testJavaRecursive() throws Exception
	{
		TCP.pushContext(new HashMap());
		TCP.getContext().put("name", "${firstname} ${lastname}");

		TemplateSource ts = new TemplateSource(ResourceUtil.newInputStreamFromResourceLocation("java06_in.txt"));
		ICommand cmd = TemplateFactory.newRecursiveTemplateInstance(ts);
		TCP.popContext();
		TCP.getContext().put("firstname", "Uli");
		TCP.getContext().put("lastname", "Ehrke");
		cmd.execute();
	}

	/**
	 * @deprecated moved to 'JavassistSpockTest'
	 */
	@Test public void testJava7() throws Exception
	{
		System.out.println("\njava7:");
		String address = fileContent("java07_address.txt");
		String nameAndAddress = fileContent("java07_nameandaddress.txt");
		TCP.pushContext(new HashMap());
		TCP.getContext().put("address", address);
		TCP.getContext().put("person", nameAndAddress);

		String person = fileContent("java07_person.txt");
		TemplateSource ts = new TemplateSource(person);
		ICommand cmd = TemplateFactory.newRecursiveTemplateInstance(ts);
		TCP.popContext();
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
		InputStream fis = ResourceUtil.newInputStreamFromResourceLocation(aFilename);
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
