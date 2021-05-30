package org.collage;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.independent.IJavaTemplateCmdCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.template.TemplateCompiler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MainSM
{

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IParserCV parserCV;
	private ITreeNodeCV treeNodeCV;
	private IJavaTemplateCmdCV javaTemplateCmdCV;
	private IEvaluationCV evaluationCV;
	private IStringHandlerCV stringHandlerCV;
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV;

	@BeforeAll
	public void setUp() {
		dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
		parserCV = dbp.newBeanForInterface(IParserCV.class);
		treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
		javaTemplateCmdCV = dbp.newBeanForInterface(IJavaTemplateCmdCV.class);
		evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
		stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
		domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);

		TCP.pushContext(new HashMap());
	}
	public void tearDown() {
		TCP.popContext();
	}

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


}
