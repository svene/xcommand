package org.collage.template;

import java.util.Collections;
import java.util.List;
import org.collage.csm.parser.*;
import org.collage.csm.transition.*;
import org.collage.dom.creationhandler.RootNodeCreationHandler;
import org.collage.parser.IParserModeCV;
import org.xcommand.misc.statemachine.IState;
import org.xcommand.misc.statemachine.State;

/**
 * TODO: rename to CollageStateNetBuilder
 */
public class CollageStateNetBuilder {

    public IState build() {
        // Setup states:
        var startState = newStartState();
        var textState = new State("text");
        var javaCodeState = new State("java_code");
        var variableTextState = new State("variable_text");
        var endState = new State("End");
        var javaEolChk = new State("java_eol_chk");

        // --- Setup transitions --- :
        // --- Start->*:
        connectStartToText(startState, textState); // Start -> Text
        connectStartToJavaCode(startState, javaCodeState); // Start -> java_code
        connectStartToVariableText(startState, variableTextState); // Start -> variable_text
        connectStartToEof(startState, endState); // Start -> End

        // --- Text->*:
        connectTextToTextTransition(textState, textState); // Text -> Text
        connectTextToTextIfEol(textState, textState); // Text -> Text (EOL)
        connectTextToJavaCode(textState, javaCodeState); // Text -> java_code
        connectTextToVariableText(textState, variableTextState); // Text -> variable_text
        connectTextToEnd(textState, endState); // Text -> End

        // --- java_code->*:
        connectJavaCodeToJavaCode(javaCodeState, javaCodeState); // java_code -> java_code
        connectJavaCodeToJavaEolChk(javaCodeState, javaEolChk); // java_code -> JavaEolChk
        connectJavaCodeToEnd(javaCodeState, endState); // java_code -> End

        // --- variable_text->*:
        connectVariableTextToVariableText(variableTextState, variableTextState); // variable_text -> variable_text
        connectVariableTextToText(variableTextState, textState); // variable_text -> text
        connectVariableTextToEnd(variableTextState, endState); // variable_text -> End

        // --- javaEolChk->*:
        connectJavaEolChkToTextIfEol(javaEolChk, textState); // java_code -> java_code
        connectJavaEolChkToTextIfText(javaEolChk, textState); // java_code -> java_code

        return startState;
    }

    private IState newStartState() {
        var state = new State("Start");
        state.getExitStateNotifier().registerObserver(new RootNodeCreationHandler());
        return state;
    }

    private void connectStartToText(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(
                        aFromState,
                        aToState,
                        IParserModeCV.KEY_TEXT,
                        List.of(CsmCommands.startTextCommand, CsmCommands.appendTextCommand));
    }

    private void connectStartToJavaCode(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(
                        aFromState, aToState, IParserModeCV.KEY_JAVA_START_MODE, List.of(CsmCommands.startJavaCommand));
    }

    private void connectStartToVariableText(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(aFromState, aToState, IParserModeCV.KEY_VAR_START_MODE, Collections.emptyList());
    }

    private void connectStartToEof(IState aFromState, IState aToState) {
        new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, Collections.emptyList());
    }

    private void connectTextToTextTransition(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(aFromState, aToState, IParserModeCV.KEY_TEXT, List.of(CsmCommands.appendTextCommand));
    }

    private void connectTextToTextIfEol(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(aFromState, aToState, IParserModeCV.KEY_EOL, List.of(CsmCommands.appendEolCommand));
    }

    private void connectTextToJavaCode(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(
                        aFromState,
                        aToState,
                        IParserModeCV.KEY_JAVA_START_MODE,
                        List.of(CsmCommands.flushTextCommand, CsmCommands.startJavaCommand));
    }

    private void connectTextToVariableText(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(aFromState, aToState, IParserModeCV.KEY_VAR_START_MODE, List.of(CsmCommands.flushTextCommand));
    }

    private void connectTextToEnd(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(aFromState, aToState, IParserModeCV.KEY_EOF, List.of(CsmCommands.flushTextCommand));
    }

    private void connectJavaCodeToJavaCode(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(
                        aFromState,
                        aToState,
                        IParserModeCV.KEY_JAVA_CODE_MODE,
                        List.of(CsmCommands.appendJavaCodeCommand));
    }

    private void connectJavaCodeToJavaEolChk(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(aFromState, aToState, IParserModeCV.KEY_JAVA_END_MODE, List.of(CsmCommands.flushJavaCommand));
    }

    private void connectJavaCodeToEnd(IState aFromState, IState aToState) {
        new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, Collections.emptyList());
    }

    private void connectVariableTextToVariableText(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(
                        aFromState,
                        aToState,
                        IParserModeCV.KEY_VAR_NAME_MODE,
                        List.of(CsmCommands.createVariableDomNodeCommand));
    }

    private void connectVariableTextToText(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(aFromState, aToState, IParserModeCV.KEY_VAR_END_MODE, List.of(CsmCommands.startTextCommand));
    }

    private void connectVariableTextToEnd(IState aFromState, IState aToState) {
        new CollageStateConnector().connect(aFromState, aToState, IParserModeCV.KEY_EOF, Collections.emptyList());
    }

    private void connectJavaEolChkToTextIfEol(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(aFromState, aToState, IParserModeCV.KEY_EOL, List.of(CsmCommands.startTextCommand));
    }

    private void connectJavaEolChkToTextIfText(IState aFromState, IState aToState) {
        new CollageStateConnector()
                .connect(
                        aFromState,
                        aToState,
                        IParserModeCV.KEY_TEXT,
                        List.of(CsmCommands.startTextCommand, CsmCommands.appendTextCommand));
    }
}
