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

	private ITreeNode compileTemplate() throws FileNotFoundException
	{
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		new DefaultDomNodeCreationHandlerInitializer().execute();
		
		parserCV.setInputStream(ResourceUtil.newInputStreamFromResourceLocation("in.txt"));
		new TemplateCompiler().execute();
		return treeNodeCV.getTreeNode();
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
	private ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
