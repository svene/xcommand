package org.xcommand.pattern.observer;

public final class ConditionObserverState {
    public final INotifier trueNotifier = new BasicNotifier();
    public final INotifier falseNotifier = new BasicNotifier();
}
