package org.collage;

import org.collage.csm.CollageStateMachine;
import org.collage.parser.ParserModeCV;
import org.collage.parser.ParserCV;
import org.collage.template.TemplateCompiler;
import org.collage.dom.ast.IDomNode;
import org.collage.dom.DomCV;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.dom.evaluator.NodeVisitor;
import org.collage.dom.evaluator.EvaluationCV;
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

		ModeContextView.setMode(ctx, ParserModeCV.KEY_TEXT);
		ParserCV.setValue(ctx, "s");
		sm.execute(ctx);
	}
	private void doit2()
	{
		TemplateCompiler tc = new TemplateCompiler();
		Map ctx = new HashMap();
		DomNodeCreationHandlerCV.setProduceJavaSource(TemplateCompiler.getConfigCtx(), Boolean.FALSE);
		String tt = "hallo ${firstname}.\nWie gehts?\n";
		InputStream is = new ByteArrayInputStream(tt.getBytes());
		ParserCV.setInputStream(ctx, is);
		ctx.putAll(TemplateCompiler.getConfigCtx());
		tc.execute(ctx);

		IDomNode rootNode = DomCV.getRootNode(ctx);
		NodeVisitor nv = new org.collage.dom.evaluator.text.Evaluator();
		ctx = new HashMap();
		DomCV.setRootNode(ctx, rootNode);
		EvaluationCV.setWriter(ctx, new PrintWriter(System.out));
		ctx.put("firstname", "Uli");
		nv.execute(ctx);
	}
	private void doit3()
	{
		TemplateCompiler tc = new TemplateCompiler();
		Map ctx = new HashMap();
		DomNodeCreationHandlerCV.setProduceJavaSource(TemplateCompiler.getConfigCtx(), Boolean.TRUE);
		String tt = "hallo <?java int i = 1;?> ${firstname}.\nWie gehts?\n";
		InputStream is = new ByteArrayInputStream(tt.getBytes());
		ParserCV.setInputStream(ctx, is);
		ctx.putAll(TemplateCompiler.getConfigCtx());
		tc.execute(ctx);

		// Evaluate using DomDumper:
		IDomNode rootNode = DomCV.getRootNode(ctx);
		NodeVisitor nv = new Evaluator();
		ctx = new HashMap();
		DomCV.setRootNode(ctx, rootNode);
		nv.execute(ctx);

		nv = new org.collage.dom.evaluator.java.javassist.Evaluator();
		ctx = new HashMap();
		DomCV.setRootNode(ctx, rootNode);
		nv.execute(ctx);

	}
}
