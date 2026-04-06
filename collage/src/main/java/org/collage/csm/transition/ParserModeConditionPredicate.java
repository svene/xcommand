package org.collage.csm.transition;

import org.collage.parser.IParserModeCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.ConditionObserverState;

public class ParserModeConditionPredicate implements ICommand {

    private final String parserMode;
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IParserModeCV parserModeCV = dbp.newBeanForInterface(IParserModeCV.class);
    public final ConditionObserverState state = new ConditionObserverState();

    public ParserModeConditionPredicate(String aParserMode) {
        parserMode = aParserMode;
    }

    @Override
    public void execute() {
        var notifier = parserModeCV.getMode().equals(parserMode) ? state.trueNotifier : state.falseNotifier;
        notifier.execute();
    }
}
