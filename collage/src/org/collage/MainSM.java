package org.collage;

import org.collage.csm.CollageStateMachine;
import org.collage.parser.ParserModeContextView;
import org.collage.parser.ParserContextView;
import org.collage.template.TemplateCompiler;
import org.collage.dom.ast.IDomNode;
import org.collage.dom.DomContextView;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;
import org.collage.dom.evaluator.NodeVisitor;
import org.collage.dom.evaluator.EvaluationContextView;
import org.collage.dom.evaluator.domdumper.Evaluator;
import org.xcommand.core.multi.ModeContextView;
import org.xcommand.misc.statemachine.StateMachine;

import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

public class MainSM
{
	public static void main(String[] args)
	{
		MainSM m = new MainSM();
		m.doit3();
	}

	private void doit1()
	{
		StateMachine sm = new CollageStateMachine();
		Map ctx = new HashMap();

		ModeContextView.setMode(ctx, ParserModeContextView.KEY_TEXT);
		ParserContextView.setValue(ctx, "s");
		sm.execute(ctx);
	}
	private void doit2()
	{
		TemplateCompiler tc = new TemplateCompiler();
		Map ctx = new HashMap();
		DomNodeCreationHandlerContextView.setProduceJavaSource(TemplateCompiler.getConfigCtx(), Boolean.FALSE);
		String tt = "hallo ${firstname}.\nWie gehts?\n";
		InputStream is = new ByteArrayInputStream(tt.getBytes());
		ParserContextView.setInputStream(ctx, is);
		ctx.putAll(TemplateCompiler.getConfigCtx());
		tc.execute(ctx);

		IDomNode rootNode = DomContextView.getRootNode(ctx);
		NodeVisitor nv = new org.collage.dom.evaluator.text.Evaluator();
		ctx = new HashMap();
		DomContextView.setRootNode(ctx, rootNode);
		EvaluationContextView.setWriter(ctx, new PrintWriter(System.out));
		ctx.put("firstname", "Uli");
		nv.execute(ctx);
	}
	private void doit3()
	{
		TemplateCompiler tc = new TemplateCompiler();
		Map ctx = new HashMap();
		DomNodeCreationHandlerContextView.setProduceJavaSource(TemplateCompiler.getConfigCtx(), Boolean.TRUE);
		String tt = "hallo <?java int i = 1;?> ${firstname}.\nWie gehts?\n";
		InputStream is = new ByteArrayInputStream(tt.getBytes());
		ParserContextView.setInputStream(ctx, is);
		ctx.putAll(TemplateCompiler.getConfigCtx());
		tc.execute(ctx);

		// Evaluate using DomDumper:
		IDomNode rootNode = DomContextView.getRootNode(ctx);
		NodeVisitor nv = new Evaluator();
		ctx = new HashMap();
		DomContextView.setRootNode(ctx, rootNode);
		nv.execute(ctx);

		nv = new org.collage.dom.evaluator.java.javassist.Evaluator();
		ctx = new HashMap();
		DomContextView.setRootNode(ctx, rootNode);
		nv.execute(ctx);

	}
}
