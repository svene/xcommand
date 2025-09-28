package org.xcommand.misc.statemachine;

import org.xcommand.core.ICommand;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.pattern.observer.StoppableNotifier;

public interface IState extends ICommand {
    String getName();

    void setName(String aName);

    INotifier getExitStateNotifier();

    INotifier getExecuteStateNotifier();

    INotifier getEnterStateNotifier();

    StoppableNotifier getExecuteNotifier();
}
