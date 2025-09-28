package org.xcommand.pattern.observer;

import java.util.ArrayList;
import java.util.List;
import org.xcommand.core.ICommand;

public abstract class AbstractBasicNotifier implements INotifier {
    protected final List<ICommand> observers = new ArrayList<>();

    @Override
    public void registerObserver(ICommand aObserver) {
        observers.add(aObserver);
    }
}
