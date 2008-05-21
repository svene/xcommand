package org.collage.dom.evaluator.java.javassist;

import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;
import org.xcommand.core.ICommand;
import org.collage.dom.ast.DomEventHandlerProvider;

public class JavassistTraverser extends NotifyingTreeNodeTraverser
{
	public JavassistTraverser()
	{
		// Setup:
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		JavassistHandlerProvider jhp = new JavassistHandlerProvider();
		hp.getRootNotifier().registerObserver(jhp.newEnterRootObserver());
		hp.getTextNotifier().registerObserver(jhp.newTextObserver());
		hp.getVariableNotifier().registerObserver(jhp.newVariableObserver());
		hp.getJavaNotifier().registerObserver(jhp.newJavaObserver());


		// TODO: think about this:
		ICommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		getEnterNodeNotifier().registerObserver(cmd);

		// RootNode-exit:
		hp = new DomEventHandlerProvider();
		hp.getRootNotifier().registerObserver(jhp.newExitRootObserver());
		cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		getExitNodeNotifier().registerObserver(cmd);
	}
}
