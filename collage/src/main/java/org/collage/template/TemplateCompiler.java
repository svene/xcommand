package org.collage.template;

import org.collage.jcc.ParseException;
import org.collage.jcc.TemplateParser;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.misc.statemachine.StateMachine;
import org.xcommand.misc.statemachine.IStateCV;
import org.xcommand.template.parser.IParserCV;

import java.io.InputStream;

public class TemplateCompiler implements ICommand
{

// --- Processing ---

	@Override
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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IParserCV parserCV = (IParserCV) dbp.newBeanForInterface(IParserCV.class);
	private IStateCV stateCV = (IStateCV) dbp.newBeanForInterface(IStateCV.class);
}
