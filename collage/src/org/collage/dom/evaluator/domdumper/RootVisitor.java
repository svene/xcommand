package org.collage.dom.evaluator.domdumper;

import org.xcommand.pattern.visitor.IVisitor;
import org.xcommand.pattern.visitor.IElementToVisitorMap;

import java.util.Map;

/**
 * TODO: continue
 */
public class RootVisitor implements IVisitor
{
	public void execute(Map aCtx)
	{
		while (hasNextElement())
		{
			Object element = getNextElement();
			IVisitor visitor = (IVisitor) (e2vm.getMap().get(element.getClass()));
			visitor.execute(aCtx);
		}
	}

	private boolean hasNextElement()
	{
		return false;
	}

	/** TODO: use an iterator to get the next element */
	private Object getNextElement()
	{
		return null;
	}

// --- Implementation ---

	IElementToVisitorMap e2vm = new DomDumperElementToVisitorMap();
}
