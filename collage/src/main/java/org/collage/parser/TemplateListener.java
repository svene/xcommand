package org.collage.parser;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.collage.parser.antlr.TemplateLexer;
import org.collage.parser.antlr.TemplateParserBaseListener;
import org.xcommand.template.parser.IParserCV;

@Slf4j
public class TemplateListener extends TemplateParserBaseListener {

    private final TemplateNotifiers notifiers;
    private final IParserCV parserCV;
    private final IParserModeCV parserModeCV;

    public TemplateListener(TemplateNotifiers notifiers, IParserCV parserCV, IParserModeCV parserModeCV) {
        this.notifiers = notifiers;
        this.parserCV = parserCV;
        this.parserModeCV = parserModeCV;
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        Token t = node.getSymbol();

        parserCV.setToken(t);
        parserCV.setValue(t.getText());
        log.debug("visitTerminal: value='{}'", t.getText());

        switch (t.getType()) {
            case TemplateLexer.TEXT -> {
                log.debug("visitTerminal: TemplateLexer.TEXT: text='{}'", t.getText());
                parserModeCV.setMode(IParserModeCV.KEY_TEXT);
                notifiers.textNotifier.execute();
            }
            case TemplateLexer.VAR_START -> {
                parserModeCV.setMode(IParserModeCV.KEY_VAR_START_MODE);
                notifiers.varStartNotifier.execute();
            }
            case TemplateLexer.VAR_NAME -> {
                parserModeCV.setMode(IParserModeCV.KEY_VAR_NAME_MODE);
                notifiers.varNameNotifier.execute();
            }
            case TemplateLexer.VAR_END -> {
                parserModeCV.setMode(IParserModeCV.KEY_VAR_END_MODE);
                notifiers.varEndNotifier.execute();
            }
            case TemplateLexer.JAVA_START -> {
                parserModeCV.setMode(IParserModeCV.KEY_JAVA_START_MODE);
                notifiers.javaStartNotifier.execute();
            }
            case TemplateLexer.JAVA_CODE -> {
                parserModeCV.setMode(IParserModeCV.KEY_JAVA_CODE_MODE);
                notifiers.javaCodeNotifier.execute();
            }
            case TemplateLexer.JAVA_END -> {
                parserModeCV.setMode(IParserModeCV.KEY_JAVA_END_MODE);
                notifiers.javaEndNotifier.execute();
            }
            case TemplateLexer.EOL -> {
                log.debug("visitTerminal: TemplateLexer.EOL: text='{}'", t.getText());
                parserModeCV.setMode(IParserModeCV.KEY_EOL);
                notifiers.eolNotifier.execute();
            }
            default -> {}
        }
    }
}
