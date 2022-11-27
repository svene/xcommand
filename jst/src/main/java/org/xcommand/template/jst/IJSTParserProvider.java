package org.xcommand.template.jst;

import org.xcommand.template.jst.parser.JSTParser;

public interface IJSTParserProvider {
	public JSTParser newJSTParser();
}
