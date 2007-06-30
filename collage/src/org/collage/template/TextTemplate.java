package org.collage.template;

import org.collage.dom.evaluator.NodeVisitor;
import org.collage.dom.evaluator.text.Evaluator;

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
