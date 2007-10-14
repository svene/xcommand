package org.collage;

import org.collage.csm.CollageStateMachine;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.dom.evaluator.EvaluationCV;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.dom.evaluator.java.independent.JavaTemplateCmdCV;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.parser.ParserCV;
import org.collage.parser.ParserModeCV;
import org.collage.template.TemplateCompiler;
import org.xcommand.core.multi.ModeContextView;
import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;
import org.xcommand.misc.statemachine.StateMachine;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class MainSM extends TestCase
{
	public static void main(String[] args)
	{
		MainSM m = new MainSM();
		m.test1();
//		m.test2();
//		m.test3();
	}

	public void test1()
	{
		StateMachine sm = new CollageStateMachine();
		Map ctx = new HashMap();

		ModeContextView.setMode(ctx, ParserModeCV.KEY_TEXT);
		ParserCV.setValue(ctx, "s");
		sm.execute(ctx);
	}
	public void test2()
	{
		TemplateCompiler tc = new TemplateCompiler();
		Map ctx = new HashMap();
		DomNodeCreationHandlerCV.setProduceJavaSource(TemplateCompiler.getConfigCtx(), Boolean.FALSE);
		InputStream is = new ByteArrayInputStream("hallo ${firstname}.\nWie gehts?\n".getBytes());
		ParserCV.setInputStream(ctx, is);
		ctx.putAll(TemplateCompiler.getConfigCtx());
		tc.execute(ctx);

		ITreeNode rootNode = TreeNodeCV.getTreeNode(ctx);
		TextTraverser tt = new TextTraverser();
		ctx = new HashMap();
		TreeNodeCV.setTreeNode(ctx, rootNode);
		EvaluationCV.setWriter(ctx, new PrintWriter(System.out));
		ctx.put("firstname", "Uli");
		tt.execute(ctx);
	}
	public void test3()
	{
		TemplateCompiler tc = new TemplateCompiler();
		Map ctx = new HashMap();
		DomNodeCreationHandlerCV.setProduceJavaSource(TemplateCompiler.getConfigCtx(), Boolean.TRUE);
		InputStream is = new ByteArrayInputStream("hallo <?java int i = 1;?> ${firstname}.\nWie gehts?\n".getBytes());
		ParserCV.setInputStream(ctx, is);
		ctx.putAll(TemplateCompiler.getConfigCtx());
		tc.execute(ctx);

		// Evaluate using DomDumper:
		ITreeNode rootNode = TreeNodeCV.getTreeNode(ctx);

//		NodeVisitor nv = new org.collage.dom.evaluator.java.javassist.Evaluator();
		JavassistTraverser tt = new JavassistTraverser();
		ctx = new HashMap();
		StringHandlerCV.setString(ctx, "dummy");
		TreeNodeCV.setTreeNode(ctx, rootNode);
		tt.execute(ctx);
		IXCommand cmd = JavaTemplateCmdCV.getTemplateComand(ctx);

		ctx = new HashMap();
		ctx.put("firstname", "Sven");
		EvaluationCV.setWriter(ctx, new PrintWriter(System.out));
		cmd.execute(ctx);

		// TODO: continue here (29.7.2007):
	}
}
