package org.xcommand.template.jst.parser;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xcommand.template.jst.DefaultJSTParserProvider;
import org.xcommand.template.jst.IJSTParserCV;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;

import static junit.framework.Assert.assertEquals;

public class JSTParserTester
{

	@Before
	public void initializeContext() throws Exception
	{
		TCP.pushContext(new HashMap());
		jstParserCV.setGeneratedJavaCode(new StringBuffer());
	}

	@After
	public void tearDownContext() throws Exception
	{
		TCP.popContext();
	}

	@Test
	public void test1() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there!".getBytes());
		JSTParser parser = newJSTParser(is);

		jstParserCV.setGeneratedJavaCode(new StringBuffer());
		parser.Start();
		assertEquals("hi there!", jstParserCV.getGeneratedJavaCode().toString());
	}

	@Test
	public void test2() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there! /*#some comment#*/".getBytes());
		JSTParser parser = newJSTParser(is);
		parser.Start();

		assertEquals("hi there! $s(\"some comment\");", jstParserCV.getGeneratedJavaCode().toString());
	}

	@Test
	public void test3() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there! /*#af $jv{somename} jj#*/".getBytes());
		JSTParser parser = newJSTParser(is);
		parser.Start();

		assertEquals("hi there! $s(\"af \");$s(somename);$s(\" jj\");", jstParserCV.getGeneratedJavaCode().toString());
	}

	@Test
	public void test4() throws Exception
	{
		InputStream is = new FileInputStream("testdata/T1.txt");
		JSTParser parser = newJSTParser(is);
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

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IJSTParserCV jstParserCV = (IJSTParserCV) dbp.newBeanForInterface(IJSTParserCV.class);
}
