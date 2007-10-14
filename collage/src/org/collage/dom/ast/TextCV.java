package org.collage.dom.ast;

import java.util.Map;

public class TextCV
{

// --- Access ---

	public static Text getText(Map aCtx)
	{
		return (Text) aCtx.get(KEY_TEXT);
	}

// --- Setting ---

	public static void setText(Map aCtx, Text aText)
	{
		aCtx.put(KEY_TEXT, aText);
	}

// --- Implementation ---

	private static final String NS = "org.collage.dom.ast.TextCV.";
	private static final String KEY_TEXT = NS + "TEXT";
}
