package org.collage.dom.creationhandler;

import org.xcommand.core.ICommand;
import org.xcommand.pattern.observer.INotifier;

public interface IDomNodeCreationHandlerCV {
    String getValue();

    ICommand getDomNodeCreationHandler();

    Boolean getProduceJavaSource();

    INotifier getCreateTextNodeRequestNotifier();

    INotifier getCreateVariableNodeRequestNotifier();

    INotifier getCreateJavaNodeRequestNotifier();

    INotifier getCreateRootNodeRequestNotifier();

    void setValue(String aValue);

    void setProduceJavaSource(Boolean aProduceJavaSource);

    void setCreateTextNodeRequestNotifier(INotifier aNotifier);

    @Deprecated
    void setDomNodeCreationHandler(ICommand aDomNodeCreationHandler);

    void setCreateVariableNodeRequestNotifier(INotifier aNotifier);

    void setCreateJavaNodeRequestNotifier(INotifier aNotifier);

    void setCreateRootNodeRequestNotifier(INotifier aNotifier);
}
