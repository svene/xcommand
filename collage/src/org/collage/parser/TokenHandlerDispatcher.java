package org.collage.parser;

import org.collage.parser.*;
import org.xcommand.core.multi.ModeBasedCommandDispatcher;
import org.xcommand.core.NopCommand;

import java.util.HashMap;
import java.util.Map;

public class TokenHandlerDispatcher extends ModeBasedCommandDispatcher
{

// --- Initialization ---

	public TokenHandlerDispatcher()
	{
		setModeCommandMap(configCtx);
	}

// --- Implementation ---

	private static Map configCtx = new HashMap();

	static
	{
		configCtx.put(ParserModeContextView.KEY_START_MODE, new StartTokenHandler());
		configCtx.put(ParserModeContextView.KEY_VAR_START_MODE, new VarStartTokenHandler());
		configCtx.put(ParserModeContextView.KEY_VAR_NAME_MODE, new VarNameTokenHandler());
		configCtx.put(ParserModeContextView.KEY_VAR_END_MODE, new NopCommand());
		configCtx.put(ParserModeContextView.KEY_JAVA_START_MODE, new JavaStartTokenHandler());
		configCtx.put(ParserModeContextView.KEY_JAVA_CODE_MODE, new JavaCodeTokenHandler());
		configCtx.put(ParserModeContextView.KEY_JAVA_END_MODE, new JavaEndTokenHandler());
		configCtx.put(ParserModeContextView.KEY_TEXT, new TextTokenHandler());
		configCtx.put(ParserModeContextView.KEY_EOL, new EolTokenHandler());
		configCtx.put(ParserModeContextView.KEY_EOF, new EofTokenHandler());
	}
}
