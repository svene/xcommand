package org.xcommand.datastructure.tree.specifictreenode.domain;

import org.xcommand.datastructure.tree.TreeNode;

import java.util.List;
import java.io.PrintWriter;

public class TreeNode2 extends TreeNode
{

	public void treeElement2Routine(List aList, PrintWriter aPrintWriter)
	{
		String msg = "TreeNode2.treeElement2Routine()";
		if (aList != null)
		{
			aList.add(msg);
		}
		if (aPrintWriter != null)
		{
			aPrintWriter.println(msg);
		}
	}

}
