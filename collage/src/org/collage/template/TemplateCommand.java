package org.collage.template;

import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCV;
import org.collage.dom.evaluator.EvaluationCV;
import org.collage.dom.evaluator.common.StringHandlerCV;

import java.io.Writer;
import java.io.StringWriter;
import java.util.Map;

public abstract class TemplateCommand implements IXCommand
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

	public void execute(Map aCtx)
	{
		// If `writer' is available output will be written to it. Otherwise
		// it will be written to a String, which is available via `StringHandlerCV.getString(aCtx)'
		boolean noWriter = getWriter() == null;
		StringWriter sw = null;
		if (noWriter)
		{
			sw = new StringWriter(1024);
			EvaluationCV.setWriter(aCtx, sw);
		}
		else
		{
			EvaluationCV.setWriter(aCtx, getWriter());
		}
		TreeNodeCV.setTreeNode(aCtx, rootNode);
		getNotifyingTreeNodeTraverser().execute(aCtx);
		if (noWriter)
		{
			StringHandlerCV.setString(aCtx, sw.toString());
		}
	}


// --- Implementation ---

	private Writer writer;
	protected ITreeNode rootNode;
	protected NotifyingTreeNodeTraverser notifyingTreeNodeTraverser;
}
