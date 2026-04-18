package org.xcommand.template.parser;

import java.io.InputStream;
import java.io.PrintStream;
import org.xcommand.core.TCP;

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

    String NS = IParserCV.class.getName() + ".";
    String KEY_TRACE_STREAM = NS + "TraceStream";

    static boolean hasTraceStream() {
        return TCP.getContext().containsKey(KEY_TRACE_STREAM);
    }
}
