package org.xcommand.template.jst;

import java.util.Map;
import java.io.InputStream;

public class JSTParserCV
{

// --- Access ---

	public static StringBuffer getGeneratedJavaCode(Map aCtx)
	{
		return (StringBuffer) aCtx.get(KEY_GENERATED_JAVA_CODE);
	}
	public static InputStream getInputStream(Map aCtx)
	{
		return (InputStream) aCtx.get(KEY_INPUT_STREAM);
	}
	public static String getEncoding(Map aCtx)
	{
		return (String) aCtx.get(KEY_ENCODING);
	}

// --- Setting ---

	public static void setGeneratedJavaCode(Map aCtx, StringBuffer aGeneratedJavaCode)
	{
		aCtx.put(KEY_GENERATED_JAVA_CODE, aGeneratedJavaCode);
	}
	public static void setInputStream(Map aCtx, InputStream aInputStream)
	{
		aCtx.put(KEY_INPUT_STREAM, aInputStream);
	}
	public static void setEncoding(Map aCtx, String aEncoding)
	{
		aCtx.put(KEY_ENCODING, aEncoding);
	}

// --- Implementation ---

	private static final String NS = "org.xcommand.template.jst.JSTParserCV.";
	private static final String KEY_GENERATED_JAVA_CODE = NS + "GENERATED_JAVA_CODE";
	private static final String KEY_INPUT_STREAM = NS + "INPUT_STREAM";
	private static final String KEY_ENCODING = NS + "ENCODING";

}
