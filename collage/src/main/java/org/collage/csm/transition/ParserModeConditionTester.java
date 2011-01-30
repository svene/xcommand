package org.collage.csm.transition;

import org.xcommand.pattern.observer.ConditionObserver;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IParserModeCV parserModeCV = (IParserModeCV) dbp.newBeanForInterface(IParserModeCV.class);
}
