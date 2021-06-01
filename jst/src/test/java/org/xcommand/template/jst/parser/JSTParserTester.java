package org.xcommand.template.jst.parser;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xcommand.template.jst.DefaultJSTParserProvider;
import org.xcommand.template.jst.IJSTParserCV;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSTParserTester
{

	@BeforeEach
	public void initializeContext() {
		TCP.pushContext(new HashMap());
		jstParserCV.setGeneratedJavaCode(new StringBuffer());
	}

	@AfterEach
	public void tearDownContext() {
		TCP.popContext();
	}

	@Test
	public void test1() throws Exception
	{
		JSTParser parser = newJSTParser(new ByteArrayInputStream("hi there!".getBytes()));

		jstParserCV.setGeneratedJavaCode(new StringBuffer());
		parser.Start();
		assertEquals("hi there!", jstParserCV.getGeneratedJavaCode().toString());
	}

	@Test
	public void test2() throws Exception
	{
		JSTParser parser = newJSTParser(new ByteArrayInputStream("hi there! /*#some comment#*/".getBytes()));
		parser.Start();

		assertEquals("hi there! $s(\"some comment\");", jstParserCV.getGeneratedJavaCode().toString());
	}

	@Test
	public void test3() throws Exception
	{
		JSTParser parser = newJSTParser(new ByteArrayInputStream("hi there! /*#af $jv{somename} jj#*/".getBytes()));
		parser.Start();

		assertEquals("hi there! $s(\"af \");$s(somename);$s(\" jj\");", jstParserCV.getGeneratedJavaCode().toString());
	}

	@Test
	public void test4() throws Exception
	{
		JSTParser parser = newJSTParser(new FileInputStream("testdata/T1.txt"));
		parser.Start();

		assertEquals("hi there! $s(\"af \");$s(somename);$s(\" jj\");\nhow are you?\n",
			jstParserCV.getGeneratedJavaCode().toString());
	}

	private JSTParser newJSTParser(InputStream aIs)
	{
		TCP.pushContext(new HashMap());
		jstParserCV.setInputStream(aIs);
		JSTParser parser = new DefaultJSTParserProvider().newJSTParser();
		TCP.popContext();

		return parser;
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}
