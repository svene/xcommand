package org.collage.dom.evaluator.java.javassist;

import org.collage.dom.ast.DomEventHandlerProvider;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

public class JavassistTraverser extends NotifyingTreeNodeTraverser {
    public JavassistTraverser() {
        // Setup:
        var hp = new DomEventHandlerProvider();
        var jhp = new JavassistHandlerProvider();
        hp.getRootNotifier().registerObserver(jhp.newEnterRootObserver());
        hp.getTextNotifier().registerObserver(jhp.newTextObserver());
        hp.getVariableNotifier().registerObserver(jhp.newVariableObserver());
        hp.getJavaNotifier().registerObserver(jhp.newJavaObserver());

        // TODO: think about this:
        var cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
        getEnterNodeNotifier().registerObserver(cmd);

        // RootNode-exit:
        hp = new DomEventHandlerProvider();
        hp.getRootNotifier().registerObserver(jhp.newExitRootObserver());
        cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
        getExitNodeNotifier().registerObserver(cmd);
    }
}
