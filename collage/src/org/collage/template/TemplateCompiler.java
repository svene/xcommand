package org.collage.template;

import org.collage.parser.*;
import org.collage.dom.creationhandler.*;
import org.collage.jcc.TemplateParser;
import org.collage.jcc.ParseException;
import org.collage.dom.creationhandler.DomNodeCreationHandlerDispatcher;
import org.xcommand.core.IXCommand;

import java.util.Map;
import java.util.HashMap;
import java.io.InputStream;

public class TemplateCompiler implements IXCommand
{

	private static Map configCtx = new HashMap();

	static
	{
		// Setup DomNodeCreationHandlerDispatcher:
		DomNodeCreationHandlerDispatcher dnchd = new DomNodeCreationHandlerDispatcher();
		DomNodeCreationHandlerContextView.setDomNodeCreationHandler(configCtx, dnchd);

		DomNodeCreationHandlerContextView.setProduceJavaSource(configCtx, Boolean.TRUE);
	}

// --- Access ---

	public static Map getConfigCtx()
	{
		return configCtx;
	}

// --- Processing ---

	public void execute(Map aCtx)
	{
		InputStream is = ParserContextView.getInputStream(aCtx);
		TemplateParser parser = new TemplateParser(is, "UTF-8");
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
