package org.collage.dom.evaluator.text;

import org.collage.dom.ast.DomEventHandlerProvider;
import org.collage.dom.evaluator.common.WriterFlusher;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

public class TextTraverser extends NotifyingTreeNodeTraverser {
    public TextTraverser() {
        // Setup:
        var hp = new DomEventHandlerProvider();
        var thp = new TextHandlerProvider();

        var cmd = thp.newTextObserver();
        hp.getTextNotifier().registerObserver(cmd);

        cmd = thp.newVariableObserver();
        hp.getVariableNotifier().registerObserver(cmd);

        cmd = thp.newJavaObserver();
        hp.getJavaNotifier().registerObserver(cmd);

        // TODO: think about this:
        cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
        getEnterNodeNotifier().registerObserver(cmd);

        // Register Writer-Flush on RootNode-exit:
        hp = new DomEventHandlerProvider();
        hp.getRootNotifier().registerObserver(new WriterFlusher());
        cmd = TreeNodeCommandFactory.newTreeNodeDomainObjectKeyedCommand(hp);
        getExitNodeNotifier().registerObserver(cmd);
    }
}
