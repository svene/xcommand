package org.collage;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.template.TemplateCompiler;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.template.parser.IParserCV;
import org.xcommand.util.ResourceUtil;

import java.io.FileNotFoundException;

public class TestHelper
{

	public TestHelper()
	{
		try
		{
			rootNode = compileTemplate();
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	ITreeNode rootNode;

// --- Implementation ---

	private ITreeNode compileTemplate() {
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		new DefaultDomNodeCreationHandlerInitializer().execute();
		
		parserCV.setInputStream(ResourceUtil.newInputStreamFromResourceLocation("in.txt"));
		new TemplateCompiler().execute();
		return treeNodeCV.getTreeNode();
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	private final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	private final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
