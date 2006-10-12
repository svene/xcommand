package org.collage.dom.evaluator.text;

import org.collage.dom.evaluator.NodeVisitor;
import org.collage.template.Template;
import org.collage.template.TemplateSource;

import java.util.Map;
import java.io.InputStream;

public class TextTemplate extends Template
{

// --- Initialization ---

	public TextTemplate(TemplateSource aTemplateSource)
	{
		super(aTemplateSource);
	}

// --- Implementation ---

	protected NodeVisitor getDefaultNodeVisitor()
	{
		return defaultTextTemplateNodeVisistor;
	}

	private static NodeVisitor defaultTextTemplateNodeVisistor = new Evaluator();



}
