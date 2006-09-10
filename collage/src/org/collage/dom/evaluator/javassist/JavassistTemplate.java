package org.collage.dom.evaluator.javassist;

import org.collage.template.Template;
import org.collage.dom.evaluator.NodeVisitor;
import org.xcommand.core.IXCommand;

import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;

public class JavassistTemplate extends Template
{

// --- Initialization ---

	public JavassistTemplate(String aTemplateText)
	{
		super(aTemplateText);
	}

	public JavassistTemplate(String aTemplateText, Map aCtx)
	{
		super(aTemplateText, aCtx);
	}

	public JavassistTemplate(InputStream aTemplateStream)
	{
		super(aTemplateStream);
	}

	public JavassistTemplate(InputStream aTemplateStream, Map aCtx)
	{
		super(aTemplateStream, aCtx);
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
		instance = JavassistContextView.getTemplateInstance(ctx);
	}

	private static NodeVisitor defaultJavassistTemplateNodeVisistor = new Evaluator();
	private IXCommand instance;

}
