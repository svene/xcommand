package org.xcommand.template.parser;

import java.io.InputStream;
import java.io.PrintStream;

public interface IParserCV
{
	public InputStream getInputStream();
	public PrintStream getTraceStream();
	public StringBuffer getStringBuffer();
	public String getValue();
	public Object getToken();
	public void setInputStream(InputStream aInputStream);
	public void setTraceStream(PrintStream aPrintStream);
	public void setStringBuffer(StringBuffer aStringBuffer);
	public void setValue(String aValue);
	public void setToken(Object aToken);
}
