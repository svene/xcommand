package org.collage.dom.ast;

import java.util.Map;

public class JavaCV
{

// --- Access ---

	public static Java getJava(Map aCtx)
	{
		return (Java) aCtx.get(KEY_JAVA);
	}

// --- Setting ---

	public static void setJava(Map aCtx, Java aJava)
	{
		aCtx.put(KEY_JAVA, aJava);
	}

// --- Implementation ---

	private static final String NS = "org.collage.dom.ast.JavaCV.";
	private static final String KEY_JAVA = NS + "JAVA";
}
