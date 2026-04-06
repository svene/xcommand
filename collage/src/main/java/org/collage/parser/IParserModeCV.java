package org.collage.parser;

public interface IParserModeCV {
    String getMode();

    void setMode(String aMode);

    String NS = "org.collage.parser.";
    String KEY_MODE = NS + "mode";
    String KEY_START_MODE = NS + "start";
    String KEY_VAR_START_MODE = NS + "varstart";
    String KEY_VAR_NAME_MODE = NS + "varname";
    String KEY_VAR_END_MODE = NS + "varend";
    String KEY_JAVA_START_MODE = NS + "javastart";
    String KEY_JAVA_CODE_MODE = NS + "javaname";
    String KEY_JAVA_END_MODE = NS + "javaend";
    String KEY_TEXT = NS + "text";
    String KEY_EOL = NS + "eol";
    String KEY_EOF = NS + "eof";
}
