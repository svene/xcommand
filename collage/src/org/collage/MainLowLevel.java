package org.collage;

import org.collage.dom.DomCV;
import org.collage.dom.ast.IDomNode;
import org.collage.dom.ast.TextNode;
import org.collage.dom.ast.VariableNode;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.dom.evaluator.EvaluationCV;
import org.collage.dom.evaluator.NodeVisitor;
import org.collage.dom.evaluator.domdumper.Evaluator;
import org.collage.dom.evaluator.text.TextNodeEvaluator;
import org.collage.dom.evaluator.text.VariableNodeEvaluator;
import org.collage.parser.ParserCV;
import org.collage.template.TemplateCompiler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MainLowLevel
{
	public static void main(String args[]) throws Exception
	{
		// ================= Template compilation ======================================
		IDomNode rootNode = compileTemplate();

		// ================= Template evaluation ======================================
		evaluateDomWithNodeVisitor(rootNode);

		System.out.println("-------------");
		evaluateDomWithDomDumper(rootNode);
		System.out.println("-------------");
		evaluateDomWithDomEvaluator(rootNode);
		System.out.println("-------------");
		evaluateDomWithStringWriter(rootNode);
	}

	private static IDomNode compileTemplate()
		throws FileNotFoundException
	{
		InputStream is;
		is = new BufferedInputStream(new FileInputStream(new File("in.txt")));
//		is = new ByteArrayInputStream("hallo ${firstname}.\nWie gehts?\n".getBytes());
		DomNodeCreationHandlerCV.setProduceJavaSource(TemplateCompiler.getConfigCtx(), Boolean.FALSE);
		TemplateCompiler templateCompiler = new TemplateCompiler();
		Map ctx = new HashMap(TemplateCompiler.getConfigCtx());
//		ParserCV.setTraceStream(ctx, System.out);
		ParserCV.setInputStream(ctx, is);
		templateCompiler.execute(ctx);
		IDomNode rootNode = DomCV.getRootNode(ctx);
		return rootNode;
	}

	private static void evaluateDomWithNodeVisitor(IDomNode aRootNode)
	{
		// Setup NodeVisitor:
		NodeVisitor nv = new NodeVisitor();
		Map configCtx = new HashMap();
		configCtx.put(TextNode.class, new TextNodeEvaluator());
		configCtx.put(VariableNode.class, new VariableNodeEvaluator());
		EvaluationCV.setWriter(configCtx, new PrintWriter(System.out));
		nv.setConfigContext(configCtx);

		// Use NodeVisitor:
		Map ctx = new HashMap();
		ctx.putAll(nv.getConfigContext());
		DomCV.setRootNode(ctx, aRootNode);
		nv.execute(ctx);
		Writer w = EvaluationCV.getWriter(ctx);
		try
		{
			w.flush();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	private static void evaluateDomWithDomDumper(IDomNode aRootNode)
	{
		// Using Evaluator (heir of NodeVisitor):
		NodeVisitor nv = new Evaluator();
		Map ctx = new HashMap();
		DomCV.setRootNode(ctx, aRootNode);
		nv.execute(ctx);
	}
	private static void evaluateDomWithDomEvaluator(IDomNode aRootNode)
	{
		NodeVisitor nv = new org.collage.dom.evaluator.text.Evaluator();
		Map ctx = new HashMap();
		DomCV.setRootNode(ctx, aRootNode);
		EvaluationCV.setWriter(ctx, new PrintWriter(System.out));
		ctx.put("firstname", "Uli");
		nv.execute(ctx);
	}
	private static void evaluateDomWithStringWriter(IDomNode aRootNode)
	{
		NodeVisitor nv = new org.collage.dom.evaluator.text.Evaluator();
		Map ctx = new HashMap();
		DomCV.setRootNode(ctx, aRootNode);
		EvaluationCV.setWriter(ctx, new StringWriter(1024));
		ctx.put("firstname", "Uli");
		nv.execute(ctx);
		String s = EvaluationCV.getWriter(ctx).toString();
		System.out.println(s);
	}

}
