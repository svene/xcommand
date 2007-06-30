package org.xcommand.datastructure.tree;

import org.xcommand.core.IXCommand;

import java.util.Map;

/**
 * HandlerProvider providing handlers based on the class of the TreeNode's
 * DomainObject
 */
public class TreeNodeDomainObjectBasedHandlerProvider extends AbstractHandlerProvider
{

	/** Use ((TreeNode)aObj).getDomainObject().getClass() as handler key */
	public Object getHandlerKey(Object aObj)
	{
		TreeNode tn = (TreeNode) aObj;
		Object domainObject = tn.getDomainObject();
		return domainObject.getClass();
	}
}
