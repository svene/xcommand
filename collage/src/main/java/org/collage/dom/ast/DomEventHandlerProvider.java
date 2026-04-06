package org.collage.dom.ast;

import java.util.Map;
import org.xcommand.datastructure.handlerprovider.MapBasedHandlerProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

public class DomEventHandlerProvider extends MapBasedHandlerProvider<Object, INotifier> {

    private static Map<Object, INotifier> handlerMap() {
        return Map.of(
                RootNode.class, new BasicNotifier(),
                Text.class, new BasicNotifier(),
                Variable.class, new BasicNotifier(),
                Java.class, new BasicNotifier());
    }

    public DomEventHandlerProvider() {
        super(handlerMap());
    }

    public INotifier getRootNotifier() {
        return getHandler(RootNode.class);
    }

    public INotifier getTextNotifier() {
        return getHandler(Text.class);
    }

    public INotifier getVariableNotifier() {
        return getHandler(Variable.class);
    }

    public INotifier getJavaNotifier() {
        return getHandler(Java.class);
    }
}
