package org.collage.template;

import org.collage.jcc.TemplateParser;
import org.xcommand.core.ICommand;

import java.io.InputStream;

public class CollageTemplateParserBuilder {
	public TemplateParser newTemplateParser(InputStream aInputStream, ICommand aCmd) {
		if (aInputStream == null) {
			throw new RuntimeException("aInputStream == null");
		}
		TemplateParser parser = new TemplateParser(aInputStream, "UTF-8");

//		parser.getStartNotifier().registerObserver(new ICommand()
//		{
//			public void execute()
//			{
//				System.out.println("****************** STARTING PARSING *********************");
//			}
//		});

		// connect statemachine to parser:
		parser.getTextNotifier().registerObserver(aCmd);
		parser.getJavaStartNotifier().registerObserver(aCmd);
		parser.getJavaCodeNotifier().registerObserver(aCmd);
		parser.getJavaEndNotifier().registerObserver(aCmd);
		parser.getVarStartNotifier().registerObserver(aCmd);
		parser.getVarNameNotifier().registerObserver(aCmd);
		parser.getVarEndNotifier().registerObserver(aCmd);
		parser.getEolNotifier().registerObserver(aCmd);
		parser.getEofNotifier().registerObserver(aCmd);

		return parser;
	}
}
