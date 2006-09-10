package org.collage.dom.ast;

import java.util.List;
import java.util.ArrayList;

public class DomNode implements IDomNode
{
	public List/*<IDomNode>*/ getChildren()
	{
		return children;
	}

	List children = new ArrayList();
}
