package org.collage.parser;

import org.antlr.v4.runtime.*;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.parser.IParserCV;
import org.collage.parser.antlr.TemplateLexer;
import org.collage.parser.antlr.TemplateParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TemplateParserWrapper {

	private final TemplateNotifiers notifiers = new TemplateNotifiers();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	private final IParserModeCV parserModeCV = dbp.newBeanForInterface(IParserModeCV.class);

	public TemplateNotifiers getNotifiers() {
		return notifiers;
	}

	public void parse(InputStream inputStream) {
		TemplateLexer lexer;
		try {
			lexer = new TemplateLexer(CharStreams.fromReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8.name())));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		TemplateParser parser = new TemplateParser(tokens);

		parser.addParseListener(new TemplateListener(notifiers, parserCV, parserModeCV));
		parser.removeErrorListeners();
		parser.addErrorListener(new DiagnosticErrorListener());
		parser.setErrorHandler(new BailErrorStrategy());

		notifiers.startNotifier.execute();
		parser.start();

		// 🔥 simulate EOF token like JavaCC
		Token eofToken = new CommonToken(Token.EOF, "<EOF>");
		parserCV.setToken(eofToken);
		parserModeCV.setMode(IParserModeCV.KEY_EOF);
		notifiers.eofNotifier.execute();
	}
}
