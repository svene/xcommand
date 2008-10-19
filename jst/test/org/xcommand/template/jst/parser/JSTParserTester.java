package org.xcommand.template.jst.parser;

import junit.framework.TestCase;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.HashMap;

import org.xcommand.template.jst.DefaultJSTParserProvider;
import org.xcommand.template.jst.IJSTParserCV;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class JSTParserTester extends TestCase
{

	protected void setUp() throws Exception
	{
		TCP.pushContext(new HashMap());
		jstParserCV.setGeneratedJavaCode(new StringBuffer());
	}

	protected void tearDown() throws Exception
	{
		TCP.popContext();
	}

	public void test1() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there!".getBytes());
		JSTParser parser = newJSTParser(is);

		jstParserCV.setGeneratedJavaCode(new StringBuffer());
		parser.Start();
		assertEquals("hi there!", jstParserCV.getGeneratedJavaCode().toString());
	}
	public void test2() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there! /*#some comment#*/".getBytes());
		JSTParser parser = newJSTParser(is);
		parser.Start();

		assertEquals("hi there! $s(\"some comment\");", jstParserCV.getGeneratedJavaCode().toString());
	}
	public void test3() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there! /*#af $jv{somename} jj#*/".getBytes());
		JSTParser parser = newJSTParser(is);
		parser.Start();

		assertEquals("hi there! $s(\"af \");$s(somename);$s(\" jj\");", jstParserCV.getGeneratedJavaCode().toString());
	}
	public void test4() throws Exception
	{
		InputStream is = new FileInputStream("jst/testdata/T1.txt");
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

	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IJSTParserCV jstParserCV = (IJSTParserCV) dbp.newBeanForInterface(IJSTParserCV.class);
}
