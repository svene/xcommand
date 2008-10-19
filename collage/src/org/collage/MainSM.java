package org.collage;

import junit.framework.TestCase;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.dom.evaluator.java.independent.IJavaTemplateCmdCV;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.TemplateCompiler;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;

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
		TCP.pushContext(new HashMap());
		TemplateCompiler tc = new TemplateCompiler();
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		InputStream is = new ByteArrayInputStream("hallo ${firstname}.\nWie gehts?\n".getBytes());
		parserCV.setInputStream(is);
		tc.execute();

		ITreeNode rootNode = treeNodeCV.getTreeNode();
		TextTraverser tt = new TextTraverser();
//		ctx = new HashMap();
		treeNodeCV.setTreeNode(rootNode);
		evaluationCV.setWriter(new PrintWriter(System.out));
		TCP.getContext().put("firstname", "Uli");
		tt.execute();
		TCP.popContext();
	}
	public void test3()
	{
		TCP.pushContext(new HashMap());
		TemplateCompiler tc = new TemplateCompiler();
//		Map ctx = new HashMap();
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.TRUE);
		InputStream is = new ByteArrayInputStream("hallo <?java int i = 1;?> ${firstname}.\nWie gehts?\n".getBytes());
		parserCV.setInputStream(is);
		tc.execute();

		// Evaluate using DomDumper:
		ITreeNode rootNode = treeNodeCV.getTreeNode();
		TCP.popContext();

//		NodeVisitor nv = new org.collage.dom.evaluator.java.javassist.Evaluator();
		JavassistTraverser tt = new JavassistTraverser();
//		ctx = new HashMap();
		TCP.pushContext(new HashMap());
		stringHandlerCV.setString("dummy");
		treeNodeCV.setTreeNode(rootNode);
		tt.execute();
		ICommand cmd = javaTemplateCmdCV.getTemplateComand();
		TCP.popContext();

		TCP.pushContext(new HashMap());
//		ctx = new HashMap();
		TCP.getContext().put("firstname", "Sven");
		evaluationCV.setWriter(new PrintWriter(System.out));
		cmd.execute();
		TCP.popContext();
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	IJavaTemplateCmdCV javaTemplateCmdCV = (IJavaTemplateCmdCV) dbp.newBeanForInterface(IJavaTemplateCmdCV.class);
	IEvaluationCV evaluationCV = (IEvaluationCV) dbp.newBeanForInterface(IEvaluationCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
