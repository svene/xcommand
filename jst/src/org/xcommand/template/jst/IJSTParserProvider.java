package org.xcommand.template.jst;

import org.xcommand.template.jst.parser.JSTParser;

import java.util.Map;

public interface IJSTParserProvider
{
	public JSTParser newJSTParser(Map aCtx);
}
