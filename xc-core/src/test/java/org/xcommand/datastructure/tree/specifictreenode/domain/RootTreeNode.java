package org.xcommand.datastructure.tree.specifictreenode.domain;

import org.xcommand.datastructure.tree.TreeNode;

import java.util.List;
import java.io.PrintWriter;

public class RootTreeNode extends TreeNode
{

	public void rootTreeElementRoutine(List<String> aList, PrintWriter aPrintWriter)
	{
		String msg = "RootTreeNode.rootTreeElementRoutine()";
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
