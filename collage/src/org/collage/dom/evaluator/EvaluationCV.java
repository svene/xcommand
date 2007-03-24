package org.collage.dom.evaluator;

import org.collage.dom.ast.IDomNode;

import java.util.Map;
import java.io.Writer;

public class EvaluationCV
{

// --- Access ---

	public static Writer getWriter(Map aCtx)
	{
		return (Writer) aCtx.get(KEY_WRITER);
	}

	public static IDomNode getNode(Map aCtx)
	{
		return (IDomNode) aCtx.get(KEY_NODE);
	}

// --- Setting ---

	public static void setWriter(Map aCtx, Writer aWriter)
	{
		aCtx.put(KEY_WRITER, aWriter);
	}

	public static void setNode(Map aCtx, IDomNode aNode)
	{
		aCtx.put(KEY_NODE, aNode);
	}

// --- Implementation ---

	public static final String NS = "org.collage.dom.text.EvaluationCV.";
	public static final String KEY_WRITER = NS + "WRITER";
	public static final String KEY_NODE = NS + "NODE";


}
