package org.xcommand.template.jst;

import java.io.InputStream;

public interface IJSTParserCV {
	public StringBuffer getGeneratedJavaCode();

	public InputStream getInputStream();

	public String getEncoding();

	public void setGeneratedJavaCode(StringBuffer aGeneratedJavaCode);

	public void setInputStream(InputStream aInputStream);

	public void setEncoding(String aEncoding);
}
