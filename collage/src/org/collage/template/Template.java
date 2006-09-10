package org.collage.template;

import org.collage.parser.ParserContextView;
import org.collage.dom.evaluator.EvaluationContextView;
import org.collage.dom.evaluator.NodeVisitor;
import org.collage.dom.ast.IDomNode;
import org.collage.dom.DomContextView;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.Writer;
import java.io.StringWriter;
import java.util.Map;
import java.util.HashMap;

public abstract class Template
{

// --- Initialization ---

	public Template(String aTemplateText)
	{
		this(aTemplateText, new HashMap());
	}
	public Template(String aTemplateText, Map aCtx)
	{
		this(new ByteArrayInputStream(aTemplateText.getBytes()), aCtx);
	}

	public Template(InputStream aTemplateStream)
	{
		this(aTemplateStream, new HashMap());
	}
	public Template(InputStream aTemplateStream, Map aCtx)
	{
		setNodeVisistor(getDefaultNodeVisitor());
		compile(aTemplateStream, aCtx);
	}

// --- Access ---

	public String getStringResult(Map aCtx)
	{
		StringWriter sw = new StringWriter(1024);
		writeTo(aCtx, sw);
		return sw.toString();
	}

	public NodeVisitor getNodeVisistor()
	{
		return nodeVisistor;
	}

// --- Setting ---

	public void setNodeVisistor(NodeVisitor aNodeVisistor)
	{
		nodeVisistor = aNodeVisistor;
	}

// --- Processing ---

	private void compile(InputStream aTemplateStream, Map aCtx)
	{
		Map ctx = new HashMap();
		ctx.putAll(TemplateCompiler.getConfigCtx());
		ctx.putAll(aCtx);
		ParserContextView.setInputStream(ctx, aTemplateStream);
		templateCompiler.execute(ctx);
		handleCompilationResult(ctx);
	}

	protected void handleCompilationResult(Map aCtx)
	{
		rootNode = DomContextView.getRootNode(aCtx);
	}

	public void writeTo(Map aCtx, Writer aWriter)
	{
		EvaluationContextView.setWriter(aCtx, aWriter);
		DomContextView.setRootNode(aCtx, rootNode);
		execute(aCtx);
	}

	public void execute(Map aCtx)
	{
		DomContextView.setRootNode(aCtx, rootNode);
		nodeVisistor.execute(aCtx);
	}

// --- Implementation ---

	protected abstract NodeVisitor getDefaultNodeVisitor();

	private static TemplateCompiler templateCompiler = new TemplateCompiler();
	private IDomNode rootNode;
	private NodeVisitor nodeVisistor;
}
