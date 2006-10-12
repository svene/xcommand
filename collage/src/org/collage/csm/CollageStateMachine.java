package org.collage.csm;

import org.collage.csm.state.*;
import org.collage.csm.transition.*;
import org.xcommand.misc.statemachine.StateMachine;
import org.xcommand.misc.statemachine.IState;
import org.xcommand.misc.statemachine.Transition;

import java.util.ArrayList;

public class CollageStateMachine extends StateMachine
{

// --- Initialization ---

	public CollageStateMachine()
	{
		// Setup states:
		IState startState = new StartState();
		setStartState(startState);
		reset();

		IState textState = new TextState();
		IState javaCodeState = new JavaCodeState();
		IState variableTextState = new VariableTextState();
		IState endState = new EndState();
		IState javaEolChk = new JavaEolChkState();

		// --- Setup transitions --- :
		// --- Start->*:
		IState state = startState;
		// Start->Text:
		state.setTransitions(new ArrayList());
		Transition t = new StartToTextTransition(state, textState);
		state.getTransitions().add(t);
		// Start->java_code:
		t = new StartToJavaCodeTransition(state, javaCodeState);
		state.getTransitions().add(t);
		// Start->variable_text:
		t = new StartToVariableTextTransition(state, variableTextState);
		state.getTransitions().add(t);
		// Start->End:
		t = new StartToEofTransition(state, endState);
		state.getTransitions().add(t);

		// --- Text->*:
		state = textState;
		// Text->Text:
		t = new TextToTextTransition(state, state);
		state.getTransitions().add(t);

		// Text->Text (EOL):
		state = textState;
		t = new TextToTextIfEolTransition(state, state);
		state.getTransitions().add(t);

		// Text->java_code:
		t = new TextToJavaCodeTransition(state, javaCodeState);
		state.getTransitions().add(t);
		// Text->variable_text:
		t = new TextToVariableTextTransition(state, variableTextState);
		state.getTransitions().add(t);
		// Text->End:
		t = new TextToEndTransition(state, endState);
		state.getTransitions().add(t);

		// --- java_code->*:
		state = javaCodeState;
		// java_code->java_code:
		t = new JavaCodeToJavaCodeTransition(state, state);
		state.getTransitions().add(t);

		// java_code->JavaEolChk:
		t = new JavaCodeToJavaEolChkTransition(state, javaEolChk);
		state.getTransitions().add(t);

		// java_code->End:
		t = new JavaCodeToEndTransition(state, endState);
		state.getTransitions().add(t);

		// --- variable_text->*:
		state = variableTextState;
		// variable_text->variable_text:
		t = new VariableTextToVariableTextTransition(state, state);
		state.getTransitions().add(t);

		// variable_text->text:
		t = new VariableTextToTextTransition(state, textState);
		state.getTransitions().add(t);

		// variable_text->End:
		t = new VariableTextToEndTransition(state, endState);
		state.getTransitions().add(t);

		// --- javaEolChk->*:
		state = javaEolChk;
		// java_code->java_code:
		t = new JavaEolChkToTextIfEolTransition(state, textState);
		state.getTransitions().add(t);

		// java_code->java_code:
		t = new JavaEolChkToTextIfTextTransition(state, textState);
		state.getTransitions().add(t);
	}

}
