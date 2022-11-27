package org.collage.template;

import org.collage.jcc.ParseException;
import org.collage.jcc.TemplateParser;
import org.xcommand.core.ICommand;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.misc.statemachine.StateMachine;
import org.xcommand.misc.statemachine.IStateCV;
import org.xcommand.template.parser.IParserCV;

import java.io.InputStream;

public class TemplateCompiler implements ICommand {

	@Override
	public void execute() {
		var is = parserCV.getInputStream();
		if (is == null) {
			throw new RuntimeException("is == null");
		}
		var sm = new StateMachine();
		var parser = new CollageTemplateParserBuilder().newTemplateParser(is, sm);

		stateCV.setState(new CollageStateMachineBuilder().newCollageStateNet());
		try {
			parser.Start();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	private final IStateCV stateCV = dbp.newBeanForInterface(IStateCV.class);
}
