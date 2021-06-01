package org.xcommand.template.parser;

import java.io.InputStream;
import java.io.PrintStream;

public interface IParserCV
{
	InputStream getInputStream();
	PrintStream getTraceStream();
	StringBuffer getStringBuffer();
	String getValue();
	Object getToken();
	void setInputStream(InputStream aInputStream);
	void setTraceStream(PrintStream aPrintStream);
	void setStringBuffer(StringBuffer aStringBuffer);
	void setValue(String aValue);
	void setToken(Object aToken);
}
