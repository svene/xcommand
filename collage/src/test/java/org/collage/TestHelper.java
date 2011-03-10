package org.collage;

import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.ListAddingStringHandler;
import org.collage.dom.evaluator.common.StringHandlerCommand;
import org.collage.dom.evaluator.common.SystemOutStringHandler;
import org.collage.dom.evaluator.common.WritingStringHandler;
import org.collage.template.TemplateCompiler;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.pattern.observer.AbstractBasicNotifier;
import org.xcommand.template.parser.IParserCV;
import org.xcommand.util.ResourceUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestHelper
{

	public TestHelper()
	{
		soutCmd = new StringHandlerCommand(new SystemOutStringHandler());
		lstCmd = new StringHandlerCommand(new ListAddingStringHandler());
		pwCmd = new StringHandlerCommand(new WritingStringHandler());

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

	public void attachTestObservers(AbstractBasicNotifier aNotifier, boolean aPrint, boolean aList)
	{
		if (aPrint) aNotifier.registerObserver(soutCmd);
		if (aList) aNotifier.registerObserver(lstCmd);
	}


	StringHandlerCommand soutCmd;
	StringHandlerCommand lstCmd;
	StringHandlerCommand pwCmd;

	ITreeNode rootNode;

// --- Implementation ---

	private ITreeNode compileTemplate() throws FileNotFoundException
	{
		InputStream is = ResourceUtil.newInputStreamFromResourceLocation("in.txt");
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		new DefaultDomNodeCreationHandlerInitializer().execute();
		
		parserCV.setInputStream(is);
		new TemplateCompiler().execute();
		return treeNodeCV.getTreeNode();
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
	private ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
