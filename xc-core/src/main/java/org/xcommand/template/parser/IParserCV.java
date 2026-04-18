package org.xcommand.template.parser;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Optional;

public interface IParserCV {
    InputStream getInputStream();

    Optional<PrintStream> getTraceStream();

    StringBuilder getStringBuilder();

    String getValue();

    Object getToken();

    void setInputStream(InputStream aInputStream);

    void setTraceStream(PrintStream aPrintStream);

    void setStringBuilder(StringBuilder aStringBuilder);

    void setValue(String aValue);

    void setToken(Object aToken);
}
