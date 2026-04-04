package org.xcommand.template.jst;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.xcommand.template.jst.parser.JSTLexer;
import org.xcommand.template.jst.parser.JSTParserBaseListener;

public class JSTListener extends JSTParserBaseListener {

	private final JSTNotifiers notifiers;

	public JSTListener(JSTNotifiers notifiers) {
		this.notifiers = notifiers;
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		Token token = node.getSymbol();

		switch (token.getType()) {
			case JSTLexer.JAVA_TEXT -> notifiers.javaTextNotifier.execute();
			case JSTLexer.JAVA_EXPR_START -> notifiers.javaVarStartNotifier.execute();
			case JSTLexer.JAVA_EXPR -> notifiers.javaVarNotifier.execute();
			case JSTLexer.JAVA_EXPR_END -> notifiers.javaVarEndNotifier.execute();
			case JSTLexer.JAVA_EOL -> notifiers.eolInJavaNotifier.execute();
			case JSTLexer.COMMENT_START -> notifiers.commentStartNotifier.execute();
			case JSTLexer.COMMENT_TEXT -> notifiers.commentTextNotifier.execute();
			case JSTLexer.COMMENT_END -> notifiers.commentEndNotifier.execute();
			case JSTLexer.COMMENT_EOL -> notifiers.eolInCommentNotifier.execute();
			default -> {} // ignore other tokens
		}
	}
}
