package org.collage.template;

import org.collage.csm.transition.*;
import org.collage.csm.parser.*;
import org.collage.dom.creationhandler.RootNodeCreationHandler;
import org.collage.parser.IParserModeCV;
import org.xcommand.misc.statemachine.IState;
import org.xcommand.misc.statemachine.State;
import org.xcommand.core.ICommand;

import java.util.List;

/**
 * TODO: rename to CollaeStateNetBuilder
 */
public class CollageStateMachineBuilder {

	public IState newCollageStateNet() {
		// Setup states:
		var startState = newStartState();
		var textState = new State("text");
		var javaCodeState = new State("java_code");
		var variableTextState = new State("variable_text");
		var endState = new State("End");
		var javaEolChk = new State("java_eol_chk");

		// --- Setup transitions --- :
		// --- Start->*:
		var state = startState;
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

	private IState newStartState() {
		var state = new State("Start");
		state.getExitStateNotifier().registerObserver(new RootNodeCreationHandler());
		return state;
	}

	private void connectStartToText(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_TEXT,
			new ICommand[]{CsmCommands.startTextCommand, CsmCommands.appendTextCommand});
	}

	private void connectStartToJavaCode(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_JAVA_START_MODE,
			new ICommand[]{CsmCommands.startJavaCommand});
	}

	private void connectStartToVariableText(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_VAR_START_MODE, null);
	}

	private void connectStartToEof(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, null);
	}

	private void connectTextToTextTransition(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_TEXT,
			new ICommand[]{CsmCommands.appendTextCommand});
	}

	private void connectTextToTextIfEol(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOL,
			new ICommand[]{CsmCommands.appendEolCommand});
	}

	private void connectTextToJavaCode(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_JAVA_START_MODE,
			new ICommand[]{CsmCommands.flushTextCommand, CsmCommands.startJavaCommand});
	}

	private void connectTextToVariableText(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_VAR_START_MODE,
			new ICommand[]{CsmCommands.flushTextCommand});
	}

	private void connectTextToEnd(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF,
			new ICommand[]{CsmCommands.flushTextCommand});
	}

	private void connectJavaCodeToJavaCode(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_JAVA_CODE_MODE,
			new ICommand[]{CsmCommands.appendJavaCodeCommand});
	}

	private void connectJavaCodeToJavaEolChk(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_JAVA_END_MODE,
			new ICommand[]{CsmCommands.flushJavaCommand});
	}

	private void connectJavaCodeToEnd(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, null);
	}

	private void connectVariableTextToVariableText(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_VAR_NAME_MODE,
			new ICommand[]{CsmCommands.createVariableDomNodeCommand});
	}

	private void connectVariableTextToText(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_VAR_END_MODE,
			new ICommand[]{CsmCommands.startTextCommand});
	}

	private void connectVariableTextToEnd(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, null);
	}

	private void connectJavaEolChkToTextIfEol(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOL,
			new ICommand[]{CsmCommands.startTextCommand});
	}

	private void connectJavaEolChkToTextIfText(IState aFromState, IState aToState) {
		new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_TEXT,
			new ICommand[]{CsmCommands.startTextCommand, CsmCommands.appendTextCommand});
	}
}
