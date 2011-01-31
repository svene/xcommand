package org.xcommand.datastructure.tree.specifictreenode;

import org.xcommand.datastructure.tree.MapBasedHandlerProvider;
import org.xcommand.datastructure.tree.specifictreenode.domain.*;

import java.util.Map;
import java.util.HashMap;

public class SpecificTreeNodeTestHandlerProvider extends MapBasedHandlerProvider
{
	{
		Map map = new HashMap();
		map.put(RootTreeNode.class, new RootTreeNodeHandler());
		map.put(TreeNode1.class, new TreeNode1Handler());
		map.put(TreeNode2.class, new TreeNode2Handler());
		setHandlerMap(map);
	}
}
