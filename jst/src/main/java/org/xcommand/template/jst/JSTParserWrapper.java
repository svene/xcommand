package org.xcommand.template.jst;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.jst.parser.JSTLexer;
import org.xcommand.template.jst.parser.JSTParser;
import org.xcommand.template.parser.IParserCV;

public class JSTParserWrapper {

    private final JSTNotifiers notifiers = new JSTNotifiers();
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
    private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);

    public JSTNotifiers getNotifiers() {
        return notifiers;
    }

    public void parse() {
        var encoding = IJSTParserCV.hasEncoding() ? jstParserCV.getEncoding() : StandardCharsets.UTF_8.name();
        // Step 1: create lexer
        JSTLexer lexer;
        try {
            lexer = new JSTLexer(CharStreams.fromReader(
                    new InputStreamReader(jstParserCV.getInputStream(), Charset.forName(encoding))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Step 2: create token stream
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Step 3: create parser
        JSTParser parser = new JSTParser(tokens);

        // Step 4: attach listener
        parser.addParseListener(new JSTListener(notifiers) {
            @Override
            public void visitTerminal(org.antlr.v4.runtime.tree.TerminalNode node) {
                Token token = node.getSymbol();
                parserCV.setToken(token);
                parserCV.setValue(token.getText());
                super.visitTerminal(node); // trigger notifier
            }
        });

        // Step 5: fire start notifier
        notifiers.startNotifier.execute();

        // Step 6: start parsing from root rule
        parser.start(); // 'start' is your root parser rule

        // Step 7: fire EOF notifier
        notifiers.eofNotifier.execute();
    }
}
