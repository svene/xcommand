package org.xcommand.misc.statemachine;

import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.pattern.observer.StoppableNotifier;

public class State implements IState {
    public State(String aName) {
        name = aName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public INotifier getExitStateNotifier() {
        return exitStateNotifier;
    }

    @Override
    public INotifier getExecuteStateNotifier() {
        return executeStateNotifier;
    }

    @Override
    public INotifier getEnterStateNotifier() {
        return enterStateNotifier;
    }

    @Override
    public StoppableNotifier getExecuteNotifier() {
        return executeNotifier;
    }

    @Override
    public void setName(String aName) {
        name = aName;
    }

    @Override
    public void execute() {
        getExecuteNotifier().execute();
    }

    private String name;

    private final INotifier exitStateNotifier = new BasicNotifier();
    private final INotifier executeStateNotifier = new BasicNotifier();
    private final INotifier enterStateNotifier = new BasicNotifier();

    private final StoppableNotifier executeNotifier = new StoppableNotifier();

    {
        // If no transition is currently able to execute, let state execute:
        executeNotifier.getNoStopRequestedNotifier().registerObserver(getExecuteStateNotifier());
    }
}
