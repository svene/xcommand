package org.collage.template;

import org.collage.parser.*;
import org.collage.dom.creationhandler.*;
import org.collage.jcc.TemplateParser;
import org.collage.jcc.ParseException;
import org.xcommand.core.IXCommand;
import org.xcommand.misc.statemachine.StateMachine;
import org.xcommand.misc.statemachine.StateCV;

import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;

public class TemplateCompiler implements IXCommand
{

	private static Map configCtx = new HashMap();

	static
	{
		DomNodeCreationHandlerCV.setProduceJavaSource(configCtx, Boolean.TRUE);
	}

// --- Access ---

	public static Map getConfigCtx()
	{
		return configCtx;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		InputStream is = ParserCV.getInputStream(aCtx);
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
