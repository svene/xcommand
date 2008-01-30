package org.collage.template;

import org.collage.jcc.ParseException;
import org.collage.jcc.TemplateParser;
import org.collage.parser.ParserCV;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.StateCV;
import org.xcommand.misc.statemachine.StateMachine;

import java.io.InputStream;
import java.util.Map;

public class TemplateCompiler implements IXCommand
{

// --- Processing ---

	public void execute(Map aCtx)
	{
		InputStream is = ParserCV.getInputStream(aCtx);
		if (is == null) throw new RuntimeException("is == null");
		StateMachine sm = new StateMachine();
		TemplateParser parser = new CollageTemplateParserBuilder().newTemplateParser(is, sm);

		StateCV.setState(aCtx, new CollageStateMachineBuilder().newCollageStateNet());
		try
		{
			parser.Start(aCtx);
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}
}
