package org.collage.template;

import org.collage.jcc.ParseException;
import org.collage.jcc.TemplateParser;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.misc.statemachine.StateMachine;
import org.xcommand.misc.statemachine.IStateCV;
import org.xcommand.template.parser.IParserCV;

import java.io.InputStream;

public class TemplateCompiler implements ICommand
{

// --- Processing ---

	public void execute()
	{
		InputStream is = parserCV.getInputStream();
		if (is == null) throw new RuntimeException("is == null");
		StateMachine sm = new StateMachine();
		TemplateParser parser = new CollageTemplateParserBuilder().newTemplateParser(is, sm);

		stateCV.setState(new CollageStateMachineBuilder().newCollageStateNet());
		try
		{
			parser.Start();
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	private IParserCV parserCV = (IParserCV) dbp.getBeanForInterface(IParserCV.class);
	private IStateCV stateCV = (IStateCV) dbp.getBeanForInterface(IStateCV.class);
}
