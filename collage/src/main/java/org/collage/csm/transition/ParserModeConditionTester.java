package org.collage.csm.transition;

import org.collage.parser.IParserModeCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.ConditionObserver;

public class ParserModeConditionTester extends ConditionObserver {

    public ParserModeConditionTester(String aParserMode) {
        parserMode = aParserMode;
    }

    @Override
    public void execute() {
        var notifier = parserModeCV.getMode().equals(parserMode) ? getTrueNotifier() : getFalseNotifier();
        notifier.execute();
    }

    private final String parserMode;
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    IParserModeCV parserModeCV = dbp.newBeanForInterface(IParserModeCV.class);
}
