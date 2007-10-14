package org.collage.dom.evaluator.java.javassist;

import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;
import org.xcommand.core.IXCommand;
import org.collage.dom.ast.DomEventHandlerProvider;

public class JavassistTraverser extends NotifyingTreeNodeTraverser
{
	public JavassistTraverser()
	{
		// Setup:
		DomEventHandlerProvider hp = new DomEventHandlerProvider();
		JavassistHandlerProvider jhp = new JavassistHandlerProvider();
		hp.getRootSubject().registerObserver(jhp.newEnterRootObserver());
		hp.getTextSubject().registerObserver(jhp.newTextObserver());
		hp.getVariableSubject().registerObserver(jhp.newVariableObserver());
		hp.getJavaSubject().registerObserver(jhp.newJavaObserver());


		// TODO: think about this:
		IXCommand cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		getEnterNodeEvent().registerObserver(cmd);

		// RootNode-exit:
		hp = new DomEventHandlerProvider();
		hp.getRootSubject().registerObserver(jhp.newExitRootObserver());
		cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
		getExitNodeEvent().registerObserver(cmd);
	}
}
