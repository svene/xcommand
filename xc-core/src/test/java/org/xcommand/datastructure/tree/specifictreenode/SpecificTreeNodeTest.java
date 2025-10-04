package org.xcommand.datastructure.tree.specifictreenode;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.datastructure.handlerprovider.MapBasedHandlerProvider;
import org.xcommand.datastructure.tree.ITreeNodeCV;
import org.xcommand.datastructure.tree.NotifyingTreeNodeTraverser;
import org.xcommand.datastructure.tree.TreeNodeCommandFactory;

class SpecificTreeNodeTest {

    IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    ITreeNodeCV treeNodeCV = (ITreeNodeCV) dbp.newBeanForInterface(ITreeNodeCV.class);
    NotifyingTreeNodeTraverser tt = new NotifyingTreeNodeTraverser();
    int counter;
    TestDataProvider tdp = new TestDataProvider();

    @Test
    void verify_that_that_Root1_traversal_notifies_Enter_Exit_NodeObservers_in_proper_order() {
        ICommandHook enterHook = Mockito.mock(ICommandHook.class);
        ICommandHook exitHook = Mockito.mock(ICommandHook.class);
        MyCommand enterCmd = new MyCommand(enterHook);
        MyCommand exitCmd = new MyCommand(exitHook);
        tt.getEnterNodeNotifier().registerObserver(enterCmd);
        tt.getExitNodeNotifier().registerObserver(exitCmd);
        treeNodeCV.setTreeNode(tdp.getRoot1());

        tt.execute();

        Mockito.verify(enterHook, Mockito.times(1)).testHook(0, tdp.getRoot1());
        Mockito.verify(enterHook, Mockito.times(1)).testHook(1, tdp.getRoot1Child());
        Mockito.verify(enterHook, Mockito.times(1)).testHook(2, tdp.getRoot1ChildChild());
        Mockito.verify(exitHook, Mockito.times(1)).testHook(3, tdp.getRoot1ChildChild());
        Mockito.verify(exitHook, Mockito.times(1)).testHook(4, tdp.getRoot1Child());
        Mockito.verify(exitHook, Mockito.times(1)).testHook(5, tdp.getRoot1());
    }

    @Test
    void verify_that_that_Root2_traversal_notifies_Enter_Exit_NodeObservers_in_proper_order() {
        ICommandHook enterHook = Mockito.mock(ICommandHook.class);
        ICommandHook exitHook = Mockito.mock(ICommandHook.class);
        MyCommand enterCmd = new MyCommand(enterHook);
        MyCommand exitCmd = new MyCommand(exitHook);
        tt.getEnterNodeNotifier().registerObserver(enterCmd);
        tt.getExitNodeNotifier().registerObserver(exitCmd);
        treeNodeCV.setTreeNode(tdp.getRoot2());

        tt.execute();

        Mockito.verify(enterHook, Mockito.times(1)).testHook(0, tdp.getRoot2());
        Mockito.verify(enterHook, Mockito.times(1)).testHook(1, tdp.getRoot2Child1());
        Mockito.verify(exitHook, Mockito.times(1)).testHook(2, tdp.getRoot2Child1());
        Mockito.verify(enterHook, Mockito.times(1)).testHook(3, tdp.getRoot2Child2());
        Mockito.verify(exitHook, Mockito.times(1)).testHook(4, tdp.getRoot2Child2());
        Mockito.verify(exitHook, Mockito.times(1)).testHook(5, tdp.getRoot2());
    }

    @Test
    void verify_that_handler_is_properly_notified_during_traversal() {
        ICommand rootTreeNodeHandler = Mockito.mock(ICommand.class);
        ICommand treeNode1Handler = Mockito.mock(ICommand.class);
        ICommand treeNode2Handler = Mockito.mock(ICommand.class);

        var map = new HashMap<Object, ICommand>();
        map.put(TestDataProvider.RootTreeNode.class, rootTreeNodeHandler);
        map.put(TestDataProvider.TreeNode1.class, treeNode1Handler);
        map.put(TestDataProvider.TreeNode2.class, treeNode2Handler);

        MapBasedHandlerProvider hp = new MapBasedHandlerProvider(map);
        ICommand cmd = TreeNodeCommandFactory.newTreeNodeKeyedCommand(hp);
        tt.getEnterNodeNotifier().registerObserver(cmd);
        treeNodeCV.setTreeNode(tdp.getRoot1());

        tt.execute();

        Mockito.verify(rootTreeNodeHandler, Mockito.times(1)).execute();
        Mockito.verify(treeNode1Handler, Mockito.times(1)).execute();
        Mockito.verify(treeNode2Handler, Mockito.times(1)).execute();
    }

    // Helpers for this test:
    private class ICommandHook {
        public void testHook(int position, Object aDomainObject) {}
    }

    private class MyCommand implements ICommand {
        ICommandHook commandHook;

        MyCommand(ICommandHook aCommandHook) {
            commandHook = aCommandHook;
        }

        @Override
        public void execute() {
            // Pass context state as arguments to 'commandHook.executeWithArguments()' so that they can be verified
            // by the mocking-fw. Just with 'execute()' this is not possible since we cannot access the context from the
            // verification:
            commandHook.testHook(counter, treeNodeCV.getDomainObject());
            counter++;
        }
    }
}
