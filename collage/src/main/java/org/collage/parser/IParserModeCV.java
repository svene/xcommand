package org.collage.parser;

public interface IParserModeCV {
	public String getMode();

	public void setMode(String aMode);

	public static final String NS = "org.collage.parser.";
	public static final String KEY_MODE = NS + "mode";
	public static final String KEY_START_MODE = NS + "start";
	public static final String KEY_VAR_START_MODE = NS + "varstart";
	public static final String KEY_VAR_NAME_MODE = NS + "varname";
	public static final String KEY_VAR_END_MODE = NS + "varend";
	public static final String KEY_JAVA_START_MODE = NS + "javastart";
	public static final String KEY_JAVA_CODE_MODE = NS + "javaname";
	public static final String KEY_JAVA_END_MODE = NS + "javaend";
	public static final String KEY_TEXT = NS + "text";
	public static final String KEY_EOL = NS + "eol";
	public static final String KEY_EOF = NS + "eof";
}
