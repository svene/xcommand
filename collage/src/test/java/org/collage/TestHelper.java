package org.collage;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.template.TemplateCompiler;
import org.jooq.lambda.Sneaky;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;
import org.xcommand.util.ResourceUtil;

public class TestHelper {

	public TestHelper() {
		Sneaky.runnable(() -> rootNode = compileTemplate()).run();
	}

	ITreeNode rootNode;

	private ITreeNode compileTemplate() {
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		new DefaultDomNodeCreationHandlerInitializer().execute();

		parserCV.setInputStream(ResourceUtil.newInputStreamFromResourceLocation("in.txt"));
		new TemplateCompiler().execute();
		return treeNodeCV.getTreeNode();
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	private final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	private final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
