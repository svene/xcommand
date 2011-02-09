package org.collage;

import org.collage.dom.evaluator.common.StringHandlerCommand;
import org.collage.dom.evaluator.common.SystemOutStringHandler;
import org.collage.dom.evaluator.common.ListAddingStringHandler;
import org.collage.dom.evaluator.common.WritingStringHandler;
import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.template.TemplateCompiler;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.misc.MessageCommand;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.pattern.observer.AbstractBasicNotifier;
import org.xcommand.template.parser.IParserCV;

import java.io.*;

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

	ICommand enterCmd = new MessageCommand()
	{
		public String getMessage()
		{
			return "entering TreeNode: " + treeNodeCV.getTreeNode().getDomainObject().getClass().getName();
		}
	};
	ICommand exitCmd = new MessageCommand()
	{
		public String getMessage()
		{
			return "leaving TreeNode: " + treeNodeCV.getTreeNode().getDomainObject().getClass().getName();
		}

	};

	ITreeNode rootNode;

// --- Implementation ---

	private ITreeNode compileTemplate() throws FileNotFoundException
	{
		final String fileName = "in.txt";
		InputStream is = newInputStreamFromFilename(fileName);
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		new DefaultDomNodeCreationHandlerInitializer().execute();
		
		parserCV.setInputStream(is);
		new TemplateCompiler().execute();
		return treeNodeCV.getTreeNode();
	}

	public static InputStream newInputStreamFromFilename(String aFileName) throws FileNotFoundException {
		final File file = new File(aFileName);

		if (!file.exists()) {
			throw new RuntimeException(String.format("file not found: '%s' (workingdir: '%s')", aFileName, new File(".").getAbsolutePath()));
		}
		return new BufferedInputStream(new FileInputStream(file));
	}

	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
	private ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
	private IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}
