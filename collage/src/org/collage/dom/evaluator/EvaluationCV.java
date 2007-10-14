package org.collage.dom.evaluator;

import java.util.Map;
import java.io.Writer;

public class EvaluationCV
{

// --- Access ---

	public static Writer getWriter(Map aCtx)
	{
		return (Writer) aCtx.get(KEY_WRITER);
	}

// --- Setting ---

	public static void setWriter(Map aCtx, Writer aWriter)
	{
		aCtx.put(KEY_WRITER, aWriter);
	}

// --- Implementation ---

	public static final String NS = "org.collage.dom.text.EvaluationCV.";
	public static final String KEY_WRITER = NS + "WRITER";
}
