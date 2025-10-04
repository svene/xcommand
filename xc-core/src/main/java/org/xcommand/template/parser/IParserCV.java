package org.xcommand.template.parser;

import java.io.InputStream;
import java.io.PrintStream;

public interface IParserCV {
    InputStream getInputStream();

    PrintStream getTraceStream();

    StringBuilder getStringBuilder();

    String getValue();

    Object getToken();

    void setInputStream(InputStream aInputStream);

    void setTraceStream(PrintStream aPrintStream);

    void setStringBuilder(StringBuilder aStringBuilder);

    void setValue(String aValue);

    void setToken(Object aToken);
}
