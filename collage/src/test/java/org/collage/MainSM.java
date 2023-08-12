package org.collage;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.independent.IJavaTemplateCmdCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.dom.evaluator.text.TextTraverser;
import org.collage.template.TemplateCompiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xcommand.core.*;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class MainSM {

	private IParserCV parserCV;
	private ITreeNodeCV treeNodeCV;
	private IJavaTemplateCmdCV javaTemplateCmdCV;
	private IEvaluationCV evaluationCV;
	private IStringHandlerCV stringHandlerCV;
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV;

	@BeforeEach
	public void setUp() {
		var dbp = DynaBeanProvider.newThreadClassMethodInstance();
		parserCV = dbp.newBeanForInterface(IParserCV.class);
		treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
		javaTemplateCmdCV = dbp.newBeanForInterface(IJavaTemplateCmdCV.class);
		evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
		stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
		domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);

		TCP.pushContext(new HashMap<>());
	}

	public void tearDown() {
		TCP.popContext();
	}

	@Test
	public void testTemplateCompiler() {
		assertThat(treeNodeCV.getTreeNode()).isNull();

		// Compile template:
		createASTforTemplateString(
			"""
				hallo ${firstname}.
				Wie gehts?
				""", Boolean.FALSE);
		// Now the root node of the AST for the compiled template string is available via 'treeNodeCV.getTreeNode()'
		assertThat(treeNodeCV.getTreeNode()).isNotNull();

	}

	@Test
	public void testTemplateEvaluation() {
		// Compile template:
		createASTforTemplateString("hallo ${firstname}.\nWie gehts?\n", Boolean.FALSE);

		// Evaluate template with a binding (firstname=Uli):
		StringWriter sw = new StringWriter();
		evaluationCV.setWriter(sw);
		TCP.getContext().put("firstname", "Uli");
		new TextTraverser().execute();
		assertThat(sw.toString()).isEqualTo("hallo Uli.\nWie gehts?\n");
	}

	@Test
	public void testTemplateWithJavaEvaluation() {
		// Compile template:
		createASTforTemplateString("hallo <?java int i = 1;?> ${firstname}.\nWie gehts?\n", Boolean.TRUE);

		// Evaluate using DomDumper:
		ITreeNode rootNode = treeNodeCV.getTreeNode();

		// Use new context to produce javaTemplateCommand:
		ICommand cmd = TCP.get(() -> {
			stringHandlerCV.setString("dummy");
			treeNodeCV.setTreeNode(rootNode);
			new JavassistTraverser().execute();
			return javaTemplateCmdCV.getTemplateComand();
		});

		// Evaluate template with a binding (firstname=Sven):
		StringWriter sw = TCP.get(() -> {
			TCP.getContext().put("firstname", "Sven");
			StringWriter sw2 = new StringWriter();
			TCP.getContext().put("writer", sw2); // todo: is there a CV for this?
			cmd.execute();
			return sw2;
		});
		assertThat(sw.toString()).isEqualTo("hallo  Sven.\nWie gehts?\n");
	}

	/**
	 * AST available after execution via 'treeNodeCV.getTreeNode()'
	 */
	private void createASTforTemplateString(String aTemplateString, Boolean aProduceJavaCode) {
		ITreeNode treeNode = TCP.get(() -> {
			domNodeCreationHandlerCV.setProduceJavaSource(aProduceJavaCode);
			new DefaultDomNodeCreationHandlerInitializer().execute();
			parserCV.setInputStream(new ByteArrayInputStream(aTemplateString.getBytes()));
			new TemplateCompiler().execute();
			return treeNodeCV.getTreeNode();
		});
		// Now put treeNode on original context:
		treeNodeCV.setTreeNode(treeNode);
	}


}
