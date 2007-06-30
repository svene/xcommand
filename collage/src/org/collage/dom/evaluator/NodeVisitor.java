package org.collage.dom.evaluator;

import org.collage.dom.*;
import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.ITreeNode;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class NodeVisitor
{

// --- Access ---

	public Map getConfigContext()
	{
		return configContext;
	}

// --- Setting ---

	public void setConfigContext(Map aConfigContext)
	{
		configContext = aConfigContext;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		//!!!IDomNode node = DomCV.getRootNode(aCtx);
		ITreeNode node = DomCV.getRootNode(aCtx);
		IXCommand dnh = (IXCommand) configContext.get(node.getClass());
		if (dnh != null)
		{
			aCtx.put("mode", "enter");
			dnh.execute(aCtx);
		}
		Iterator it = node.getChildren().iterator();
		while (it.hasNext())
		{
			node = (ITreeNode) it.next();
			EvaluationCV.setNode(aCtx, node);
			dnh = (IXCommand) configContext.get(node.getClass());
			if (dnh != null)
			{
				dnh.execute(aCtx);
			}
		}
		node = DomCV.getRootNode(aCtx);
		dnh = (IXCommand) configContext.get(node.getClass());
		if (dnh != null)
		{
			aCtx.put("mode", "exit");
			dnh.execute(aCtx);
		}
	}

// --- Implementation ---

	private Map configContext = new HashMap();
}
