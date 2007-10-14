package org.collage.dom.ast;

import org.xcommand.core.IXCommand;
import org.xcommand.datastructure.tree.ITreeNode;
import org.xcommand.datastructure.tree.TreeNodeCV;

import java.util.Map;

public class DomObjToTextTransformer implements IXCommand
{
	public void execute(Map aCtx)
	{
		ITreeNode node = TreeNodeCV.getTreeNode(aCtx);
		Text text = (Text) node.getDomainObject();
		TextCV.setText(aCtx, text);
	}
}
