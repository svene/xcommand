package org.xcommand.template.jst;

import java.io.InputStream;

public interface IJSTParserCV {
    StringBuffer getGeneratedJavaCode();

    InputStream getInputStream();

    String getEncoding();

    void setGeneratedJavaCode(StringBuffer aGeneratedJavaCode);

    void setInputStream(InputStream aInputStream);

    void setEncoding(String aEncoding);

    boolean hasEncoding();
}
