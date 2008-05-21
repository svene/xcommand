package org.collage.template;

import org.collage.csm.transition.*;
import org.collage.csm.parser.*;
import org.collage.dom.creationhandler.RootNodeCreationHandler;
import org.collage.parser.IParserModeCV;
import org.xcommand.misc.statemachine.IState;
import org.xcommand.misc.statemachine.State;
import org.xcommand.core.ICommand;

/** TODO: rename to CollaeStateNetBuilder */
public class CollageStateMachineBuilder
{

	public IState newCollageStateNet()
	{
		// Setup states:
		IState startState = newStartState();
		IState textState = new State("text");
		IState javaCodeState = new State("java_code");
		IState variableTextState = new State("variable_text");
		IState endState = new State("End");
		IState javaEolChk = new State("java_eol_chk");

		// --- Setup transitions --- :
		// --- Start->*:
		IState state = startState;
		// Start->Text:
		connectStartToText(state, textState);

		// Start->java_code:
		connectStartToJavaCode(state, javaCodeState);

		// Start->variable_text:
		connectStartToVariableText(state, variableTextState);

		// Start->End:
		connectStartToEof(state, endState);


		// --- Text->*:
		state = textState;
		// Text->Text:
		connectTextToTextTransition(state, state);


		// Text->Text (EOL):
		state = textState;

		connectTextToTextIfEol(state, state);

		// Text->java_code:
		connectTextToJavaCode(state, javaCodeState);

		// Text->variable_text:
		connectTextToVariableText(state, variableTextState);

		// Text->End:
		connectTextToEnd(state, endState);

		// --- java_code->*:
		state = javaCodeState;

		// java_code->java_code:
		connectJavaCodeToJavaCode(state, state);

		// java_code->JavaEolChk:
		connectJavaCodeToJavaEolChk(state, javaEolChk);

		// java_code->End:
		connectJavaCodeToEnd(state, endState);

		// --- variable_text->*:
		state = variableTextState;

		// variable_text->variable_text:
		connectVariableTextToVariableText(state, state);

		// variable_text->text:
		connectVariableTextToText(state, textState);

		// variable_text->End:
		connectVariableTextToEnd(state, endState);

		// --- javaEolChk->*:
		state = javaEolChk;

		// java_code->java_code:
		connectJavaEolChkToTextIfEol(state, textState);

		// java_code->java_code:
		connectJavaEolChkToTextIfText(state, textState);

		return startState;
	}

// --- Implementation ---

	private IState newStartState()
	{
		IState state = new State("Start");
		state.getExitStateNotifier().registerObserver(new RootNodeCreationHandler());
		return state;
	}

	private void connectStartToText(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_TEXT,
			new ICommand[]{new CsmStartTextCommand(), new CsmAppendTextCommand()});
	}
	private void connectStartToJavaCode(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_JAVA_START_MODE,
			new ICommand[]{new CsmStartJavaCommand()});
	}
	private void connectStartToVariableText(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_VAR_START_MODE, null);
	}
	private void connectStartToEof(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, null);
	}
	private void connectTextToTextTransition(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_TEXT,
			new ICommand[]{new CsmAppendTextCommand()});
	}
	private void connectTextToTextIfEol(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOL,
			new ICommand[]{new CsmAppendEolCommand()});
	}
	private void connectTextToJavaCode(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_JAVA_START_MODE,
			new ICommand[]{new CsmFlushTextCommand(), new CsmStartJavaCommand()});
	}
	private void connectTextToVariableText(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_VAR_START_MODE,
			new ICommand[]{new CsmFlushTextCommand()});
	}
	private void connectTextToEnd(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF,
			new ICommand[]{new CsmFlushTextCommand()});
	}
	private void connectJavaCodeToJavaCode(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_JAVA_CODE_MODE,
			new ICommand[]{new CsmAppendJavaCodeCommand()});
	}
	private void connectJavaCodeToJavaEolChk(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_JAVA_END_MODE,
			new ICommand[]{new CsmFlushJavaCommand()});
	}
	private void connectJavaCodeToEnd(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, null);
	}
	private void connectVariableTextToVariableText(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_VAR_NAME_MODE,
			new ICommand[]{new CsmCreateVariableDomNodeCommand()});
	}
	private void connectVariableTextToText(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_VAR_END_MODE,
			new ICommand[]{new CsmStartTextCommand()});
	}
	private void connectVariableTextToEnd(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, null);
	}
	private void connectJavaEolChkToTextIfEol(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOL,
			new ICommand[]{new CsmStartTextCommand()});
	}
	private void connectJavaEolChkToTextIfText(IState aFromState, IState aToState)
	{
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_TEXT,
			new ICommand[]{new CsmStartTextCommand(), new CsmAppendTextCommand()});
	}
}
