package org.collage.template;

import org.collage.jcc.TemplateParser;
import org.xcommand.core.IXCommand;

import java.io.InputStream;

public class CollageTemplateParserBuilder
{
	public TemplateParser newTemplateParser(InputStream aInputStream, IXCommand aCmd)
	{
		TemplateParser parser = new TemplateParser(aInputStream, "UTF-8");

//		parser.getStartSubject().registerObserver(new IXCommand()
//		{
//			public void execute(Map aCtx)
//			{
//				System.out.println("****************** STARTING PARSING *********************");
//			}
//		});

		// connect statemachine to parser:
		parser.getTextSubject().registerObserver(aCmd);
		parser.getJavaStartSubject().registerObserver(aCmd);
		parser.getJavaCodeSubject().registerObserver(aCmd);
		parser.getJavaEndSubject().registerObserver(aCmd);
		parser.getVarStartSubject().registerObserver(aCmd);
		parser.getVarNameSubject().registerObserver(aCmd);
		parser.getVarEndSubject().registerObserver(aCmd);
		parser.getEolSubject().registerObserver(aCmd);
		parser.getEofSubject().registerObserver(aCmd);

		return parser;
	}
}
