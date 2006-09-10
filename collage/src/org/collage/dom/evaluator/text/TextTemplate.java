package org.collage.dom.evaluator.text;

import org.collage.dom.evaluator.NodeVisitor;
import org.collage.template.Template;

import java.util.Map;
import java.io.InputStream;

public class TextTemplate extends Template
{

// --- Initialization ---

	public TextTemplate(String aTemplateText)
	{
		super(aTemplateText);
	}

	public TextTemplate(String aTemplateText, Map aCtx)
	{
		super(aTemplateText, aCtx);
	}

	public TextTemplate(InputStream aTemplateStream)
	{
		super(aTemplateStream);
	}

	public TextTemplate(InputStream aTemplateStream, Map aCtx)
	{
		super(aTemplateStream, aCtx);
	}

// --- Implementation ---

	protected NodeVisitor getDefaultNodeVisitor()
	{
		return defaultTextTemplateNodeVisistor;
	}

	private static NodeVisitor defaultTextTemplateNodeVisistor = new Evaluator();



}
