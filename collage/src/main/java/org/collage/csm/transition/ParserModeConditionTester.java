package org.collage.csm.transition;

import org.xcommand.pattern.observer.ConditionObserver;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.collage.parser.IParserModeCV;

public class ParserModeConditionTester extends ConditionObserver {

	public ParserModeConditionTester(String aParserMode) {
		parserMode = aParserMode;
	}

	@Override
	public void execute() {
		String mode = parserModeCV.getMode();
		ICommand notifier = mode.equals(parserMode) ? getTrueNotifier() : getFalseNotifier();
		notifier.execute();
	}

	private final String parserMode;
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IParserModeCV parserModeCV = dbp.newBeanForInterface(IParserModeCV.class);
}
