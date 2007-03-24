package org.collage.template;

import org.collage.dom.evaluator.NodeVisitor;
import org.collage.dom.evaluator.java.independent.JavaTemplateCmdCV;
import org.collage.dom.evaluator.java.javassist.Evaluator;
import org.xcommand.core.IXCommand;

import java.util.Map;
import java.util.HashMap;

public class JavassistTemplate extends Template
{

// --- Initialization ---

	public JavassistTemplate(TemplateSource aTemplateSource)
	{
		super(aTemplateSource);
	}

// --- Access ---

	public IXCommand getInstance()
	{
		return instance;
	}

// --- Implementation ---

	protected NodeVisitor getDefaultNodeVisitor()
	{
		return defaultJavassistTemplateNodeVisistor;
	}

	protected void handleCompilationResult(Map aCtx)
	{
		super.handleCompilationResult(aCtx);
		Map ctx = new HashMap();
		execute(ctx);
		instance = JavaTemplateCmdCV.getTemplateComand(ctx);
	}

	private static NodeVisitor defaultJavassistTemplateNodeVisistor = new Evaluator();
	private IXCommand instance;

}
