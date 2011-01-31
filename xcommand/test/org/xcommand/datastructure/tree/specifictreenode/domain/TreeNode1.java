package org.xcommand.datastructure.tree.specifictreenode.domain;

import org.xcommand.datastructure.tree.TreeNode;

import java.util.List;
import java.io.PrintWriter;

public class TreeNode1 extends TreeNode
{

	public void treeElement1Routine(List aList, PrintWriter aPrintWriter)
	{
		String msg = "TreeNode1.treeElement1Routine()";
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
