package org.xcommand.template.jst.parser;

import junit.framework.TestCase;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.Map;
import java.util.HashMap;

import org.xcommand.template.jst.JSTParserCV;
import org.xcommand.template.jst.DefaultJSTParserProvider;

public class JSTParserTester extends TestCase
{

	private Map ctx;

	protected void setUp() throws Exception
	{
		ctx = new HashMap();
		JSTParserCV.setGeneratedJavaCode(ctx, new StringBuffer());
	}

	public void test1() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there!".getBytes());
		JSTParser parser = newJSTParser(is);

		JSTParserCV.setGeneratedJavaCode(ctx, new StringBuffer());
		parser.Start(ctx);
		assertEquals("hi there!", JSTParserCV.getGeneratedJavaCode(ctx).toString());
	}
	public void test2() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there! /*#some comment#*/".getBytes());
		JSTParser parser = newJSTParser(is);
		parser.Start(ctx);

		assertEquals("hi there! $s(\"some comment\");", JSTParserCV.getGeneratedJavaCode(ctx).toString());
	}
	public void test3() throws Exception
	{
		InputStream is = new ByteArrayInputStream("hi there! /*#af $jv{somename} jj#*/".getBytes());
		JSTParser parser = newJSTParser(is);
		parser.Start(ctx);

		assertEquals("hi there! $s(\"af \");$s(somename);$s(\" jj\");", JSTParserCV.getGeneratedJavaCode(ctx).toString());
	}
	public void test4() throws Exception
	{
		InputStream is = new FileInputStream("jst/testdata/T1.txt");
		JSTParser parser = newJSTParser(is);
		parser.Start(ctx);

		assertEquals("hi there! $s(\"af \");$s(somename);$s(\" jj\");\nhow are you?\n",
			JSTParserCV.getGeneratedJavaCode(ctx).toString());
	}

	private JSTParser newJSTParser(InputStream aIs)
	{
		Map cctx = new HashMap();
		JSTParserCV.setInputStream(cctx, aIs);
		JSTParser parser = new DefaultJSTParserProvider().newJSTParser(cctx);

		return parser;
	}


}
