package org.xcommand.pattern.visitor;

import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.util.Map;
import java.util.Iterator;

public class Visitor implements IXCommand
{

// --- Initialization ---

	private Visitor()
	{
	}

	/** Factory constructor */
	public static Visitor newVisitor(IElementToVisitorMap aElementToVisitorMap, Iterator aElementIterator)
	{
		Visitor result = new Visitor();
		result.elementToVisitorMap = aElementToVisitorMap;
		result.elementIterator = aElementIterator;
		return result;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		while (elementIterator.hasNext())
		{
			// Get next element:
			Object element = elementIterator.next();
			// ... and put it on `aCtx' so that visitors can access it:
//!!			TreeNodeCV.setTreeNode(aCtx, element);
			// Get visitor for element:
			IXCommand visitor = (IXCommand) (elementToVisitorMap.getMap().get(element.getClass()));

			// let `visitor' visit `element':
			visitor.execute(aCtx);
		}
	}

// --- Implementation ---

	IElementToVisitorMap elementToVisitorMap;
	Iterator elementIterator;
}
