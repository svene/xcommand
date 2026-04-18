package org.xcommand.datastructure.tree;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.datastructure.handlerprovider.IHandlerProvider;
import org.xcommand.threadcontext.ITIn2OutCV;

class HandlerProviderBasedCommandTest {

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final ITreeNodeCV treeNodeCV = dbp.newBeanForInterface(ITreeNodeCV.class);
    private final ITIn2OutCV inoutCV = dbp.newBeanForInterface(ITIn2OutCV.class);

    @Test
    @SuppressWarnings("unchecked")
    void executesHandlerFoundByTreeNode() {
        ITreeNode node = mock(ITreeNode.class);
        IHandlerProvider<Object, ?> provider = mock(IHandlerProvider.class);
        when(provider.getHandler(node)).thenReturn(() -> inoutCV.setOutput("handled"));

        var cmd = new HandlerProviderBasedCommand(null, provider);

        TCP.start(() -> {
            treeNodeCV.setTreeNode(node);
            cmd.execute();
            assertThat(inoutCV.getOutput()).isEqualTo("handled");
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    void doesNothingWhenHandlerIsNull() {
        ITreeNode node = mock(ITreeNode.class);
        IHandlerProvider<Object, ?> provider = mock(IHandlerProvider.class);
        when(provider.getHandler(node)).thenReturn(null);

        var cmd = new HandlerProviderBasedCommand(null, provider);

        TCP.start(() -> {
            treeNodeCV.setTreeNode(node);
            cmd.execute();
            // handler was null — no output was set; accessing it would throw
        });
    }

    @Test
    @SuppressWarnings("unchecked")
    void usesHandlerKeyProviderWhenProvided() {
        ITreeNode node = mock(ITreeNode.class);
        String customKey = "customKey";
        IHandlerProvider<Object, ?> provider = mock(IHandlerProvider.class);
        when(provider.getHandler(customKey)).thenReturn(() -> inoutCV.setOutput("custom-handled"));

        var cmd = new HandlerProviderBasedCommand(tn -> customKey, provider);

        TCP.start(() -> {
            treeNodeCV.setTreeNode(node);
            cmd.execute();
            assertThat(inoutCV.getOutput()).isEqualTo("custom-handled");
        });
    }
}
