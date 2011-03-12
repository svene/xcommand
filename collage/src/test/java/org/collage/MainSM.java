package org.collage;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.dom.evaluator.java.independent.IJavaTemplateCmdCV;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.TemplateCompiler;
import org.junit.Test;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MainSM
{

	@Test
	public void testTemplateCompiler()
	{
		assertNull(treeNodeCV.getTreeNode());

		// Compile template:
		createASTforTemplateString("hallo ${firstname}.\nWie gehts?\n", Boolean.FALSE);
		// Now the root node of the AST for the compiled template string is available via 'treeNodeCV.getTreeNode()'
		assertNotNull(treeNodeCV.getTreeNode());
	}

	@Test
	public void testTemplateEvaluation()
	{
		// Compile template:
		createASTforTemplateString("hallo ${firstname}.\nWie gehts?\n", Boolean.FALSE);

		// Evaluate template with a binding (firstname=Uli):
		StringWriter sw = new StringWriter();
		evaluationCV.setWriter(sw);
		TCP.getContext().put("firstname", "Uli");
		new TextTraverser().execute();
		assertEquals("hallo Uli.\nWie gehts?\n", sw.toString());
	}

	@Test
	public void testTemplateWithJavaEvaluation()
	{
		// Compile template:
		createASTforTemplateString("hallo <?java int i = 1;?> ${firstname}.\nWie gehts?\n", Boolean.TRUE);

		// Evaluate using DomDumper:
		ITreeNode rootNode = treeNodeCV.getTreeNode();

		// Use new context to produce javaTemplateCommand:
		TCP.pushContext(new HashMap());
		stringHandlerCV.setString("dummy");
		treeNodeCV.setTreeNode(rootNode);
		new JavassistTraverser().execute();
		ICommand cmd = javaTemplateCmdCV.getTemplateComand();
		TCP.popContext();

		// Evaluate template with a binding (firstname=Sven):
		TCP.pushContext(new HashMap());
		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TCP.getContext().put("writer", sw); // todo: is there a CV for this?
		cmd.execute();
		TCP.popContext();
		assertEquals("hallo  Sven.\nWie gehts?\n", sw.toString());
	}

	/**
	 * AST available after execution via 'treeNodeCV.getTreeNode()'
	 */
	private void createASTforTemplateString(String aTemplateString, final Boolean aProduceJavaCode) {
		TCP.pushContext(new HashMap());
		domNodeCreationHandlerCV.setProduceJavaSource(aProduceJavaCode);
		new DefaultDomNodeCreationHandlerInitializer().execute();
		parserCV.setInputStream(new ByteArrayInputStream(aTemplateString.getBytes()));
		new TemplateCompiler().execute();

		// Get produced treeNode so that temporary context can be removed:
		final ITreeNode treeNode = treeNodeCV.getTreeNode();
		TCP.popContext();
		// Now put treeNode on original context:
		treeNodeCV.setTreeNode(treeNode);
	}


	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
	ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	IJavaTemplateCmdCV javaTemplateCmdCV = (IJavaTemplateCmdCV) dbp.newBeanForInterface(IJavaTemplateCmdCV.class);
	IEvaluationCV evaluationCV = (IEvaluationCV) dbp.newBeanForInterface(IEvaluationCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
