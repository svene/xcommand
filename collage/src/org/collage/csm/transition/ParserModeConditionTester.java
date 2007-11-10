package org.collage.csm.transition;

import org.xcommand.pattern.observer.ConditionObserver;
import org.xcommand.pattern.observer.ISubject;
import org.collage.parser.ParserModeCV;

import java.util.Map;

public class ParserModeConditionTester extends ConditionObserver
{

// --- Initialization ---

	public ParserModeConditionTester(String aParserMode)
	{
		parserMode = aParserMode;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		String mode = ParserModeCV.getMode(aCtx);
		ISubject notifier = mode.equals(parserMode) ? getTrueNotifier() : getFalseNotifier();
		notifier.execute(aCtx);
	}

	private String parserMode;
}
