package org.collage.csm.transition;

import org.xcommand.pattern.observer.ConditionObserver;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.collage.parser.IParserModeCV;

public class ParserModeConditionTester extends ConditionObserver
{

// --- Initialization ---

	public ParserModeConditionTester(String aParserMode)
	{
		parserMode = aParserMode;
	}

// --- Processing ---

	public void execute()
	{
		String mode = parserModeCV.getMode();
		ICommand notifier = mode.equals(parserMode) ? getTrueNotifier() : getFalseNotifier();
		notifier.execute();
	}

	private String parserMode;
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IParserModeCV parserModeCV = (IParserModeCV) dbp.getBeanForInterface(IParserModeCV.class);
}
