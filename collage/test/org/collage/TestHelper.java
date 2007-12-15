package org.collage;

import org.collage.dom.evaluator.common.StringHandlerCommand;
import org.collage.dom.evaluator.common.SystemOutStringHandler;
import org.collage.dom.evaluator.common.ListAddingStringHandler;
import org.collage.dom.evaluator.common.WritingStringHandler;
import org.collage.dom.creationhandler.DomNodeCreationHandlerCV;
import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.template.TemplateCompiler;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.MessageCommand;
import org.xcommand.datastructure.tree.TreeNodeCV;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.pattern.observer.SubjectImpl;

import java.util.Map;
import java.util.HashMap;
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
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void attachTestObservers(SubjectImpl aSubject, boolean aPrint, boolean aList)
	{
		if (aPrint) aSubject.registerObserver(soutCmd);
		if (aList) aSubject.registerObserver(lstCmd);
	}


	StringHandlerCommand soutCmd;
	StringHandlerCommand lstCmd;
	StringHandlerCommand pwCmd;

	IXCommand enterCmd = new MessageCommand()
	{
		public String getMessage(Map aCtx)
		{
			return "entering TreeNode: " + TreeNodeCV.getTreeNode(aCtx).getDomainObject().getClass().getName();
		}
	};
	IXCommand exitCmd = new MessageCommand()
	{
		public String getMessage(Map aCtx)
		{
			return "leaving TreeNode: " + TreeNodeCV.getTreeNode(aCtx).getDomainObject().getClass().getName();
		}

	};

	ITreeNode rootNode;

// --- Implementation ---

	private static ITreeNode compileTemplate()
		throws FileNotFoundException
	{
		InputStream is;
		is = new BufferedInputStream(new FileInputStream(new File("in.txt")));
//		is = new ByteArrayInputStream("hallo ${firstname}.\nWie gehts?\n".getBytes());
		DomNodeCreationHandlerCV.setProduceJavaSource(TemplateCompiler.getConfigCtx(), Boolean.FALSE);
		Map ctx = new HashMap(TemplateCompiler.getConfigCtx());
		new DefaultDomNodeCreationHandlerInitializer().execute(ctx);
		
//		ParserCV.setTraceStream(ctx, System.out);
		ParserCV.setInputStream(ctx, is);
		new TemplateCompiler().execute(ctx);
		return TreeNodeCV.getTreeNode(ctx);
	}
}
