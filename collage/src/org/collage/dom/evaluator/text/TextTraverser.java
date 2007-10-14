package org.collage.dom.evaluator.text;

import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;
import org.xcommand.core.IXCommand;
import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.evaluator.common.WriterFlusher;

public class TextTraverser extends NotifyingTreeNodeTraverser
{
	public TextTraverser()
	{
		// Setup:
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		TextHandlerProvider thp = new TextHandlerProvider();

		IXCommand cmd = thp.newTextObserver();
		hp.getTextSubject().registerObserver(cmd);

		cmd = thp.newVariableObserver();
		hp.getVariableSubject().registerObserver(cmd);

		cmd = thp.newJavaObserver();
		hp.getJavaSubject().registerObserver(cmd);

		//TODO: think about this:
		cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		getEnterNodeEvent().registerObserver(cmd);

		// Register Writer-Flush on RootNode-exit:
		hp = new DomEventHandlerProvider();
		hp.getRootSubject().registerObserver(new WriterFlusher());
		cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		getExitNodeEvent().registerObserver(cmd);
	}
}
