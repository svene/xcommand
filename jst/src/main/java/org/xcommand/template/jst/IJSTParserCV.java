package org.xcommand.template.jst;

import java.io.InputStream;
import java.util.Optional;

public interface IJSTParserCV {
    StringBuffer getGeneratedJavaCode();

    InputStream getInputStream();

    Optional<String> getEncoding();

    void setGeneratedJavaCode(StringBuffer aGeneratedJavaCode);

    void setInputStream(InputStream aInputStream);

    void setEncoding(String aEncoding);
}
