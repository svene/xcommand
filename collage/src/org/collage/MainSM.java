package org.collage;

import junit.framework.TestCase;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.dom.evaluator.EvaluationCV;
import org.collage.dom.evaluator.common.StringHandlerCV;
import org.collage.dom.evaluator.java.independent.JavaTemplateCmdCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.parser.ParserCV;
import org.collage.template.TemplateCompiler;
import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class MainSM extends TestCase
{
	public static void main(String[] args)
	{
		MainSM m = new MainSM();
		m.test2();
//		m.test3();
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
	}
}
