package org.collage.template;

import org.collage.dom.evaluator.IEvaluationCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;

import java.io.StringWriter;
import java.io.Writer;

public abstract class TemplateCommand implements ICommand
{

// --- Initialization ---

	protected TemplateCommand(ITreeNode aRootNode)
	{
		rootNode = aRootNode;
	}

// --- Access ---

	public Writer getWriter()
	{
		return writer;
	}

	protected NotifyingTreeNodeTraverser getNotifyingTreeNodeTraverser()
	{
		return notifyingTreeNodeTraverser;
	}

// --- Setting ---

	public void setWriter(Writer aWriter)
	{
		writer = aWriter;
	}

	protected void setNotifyingTreeNodeTraverser(NotifyingTreeNodeTraverser aNotifyingTreeNodeTraverser)
	{
		notifyingTreeNodeTraverser = aNotifyingTreeNodeTraverser;
	}

// --- Processing ---

	public void execute()
	{
		// If `writer' is available output will be written to it. Otherwise
		// it will be written to a String, which is available via `StringHandlerCV.getString(aCtx)'
		boolean noWriter = getWriter() == null;
		StringWriter sw = null;
		if (noWriter)
		{
			sw = new StringWriter(1024);
			evaluationCV.setWriter(sw);
		}
		else
		{
			evaluationCV.setWriter(getWriter());
		}
		treeNodeCV.setTreeNode(rootNode);
		getNotifyingTreeNodeTraverser().execute();
		if (noWriter)
		{
			stringHandlerCV.setString(sw.toString());
		}
	}


// --- Implementation ---

	private Writer writer;
	protected ITreeNode rootNode;
	protected NotifyingTreeNodeTraverser notifyingTreeNodeTraverser;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
	IEvaluationCV evaluationCV = dbp.newBeanForInterface(IEvaluationCV.class);
	IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}
